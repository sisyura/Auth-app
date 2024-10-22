package ru.nemov.authapp.ui.screen.accounts

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.nemov.authapp.domain.models.AccountData
import ru.nemov.authapp.ui.activity.LocalNavController
import ru.nemov.authapp.ui.activity.NavRoutes
import ru.nemov.authapp.ui.components.ConfirmDismissAlertDialog
import ru.nemov.authapp.ui.components.CustomButton
import ru.nemov.authapp.ui.components.TopAppBar
import ru.nemov.authapp.R
import ru.nemov.authapp.ui.theme.BackgroundDelete
import ru.nemov.authapp.ui.theme.BackgroundPrimary
import ru.nemov.authapp.ui.theme.Green
import ru.nemov.authapp.ui.theme.Red
import ru.nemov.authapp.ui.theme.Transparent
import ru.nemov.authapp.ui.theme.White
import ru.nemov.authapp.ui.theme.body1
import ru.nemov.authapp.ui.theme.customTextStyle
import ru.nemov.authapp.ui.theme.title
import ru.nemov.authapp.ui.theme.title1
import ru.nemov.authapp.utils.TOTP
import ru.nemov.authapp.utils.stringResource
import ru.nemov.authapp.utils.toast

@Composable
fun AccountListMain(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    AccountListMain(navController, modifier, viewModel = hiltViewModel())
}


@Composable
private fun AccountListMain(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AccountsViewModel
) {
    val products by viewModel.accounts.collectAsState()
    val activity = LocalContext.current as? Activity

    Scaffold(
        modifier = modifier
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                onClickNavigation = { if (!navController.navigateUp()) activity?.finish() },
                onActionClick = { }
            )
        }
    )
    { innerPadding ->
        Accounts(
            navController,
            products,
            viewModel,
            modifier.padding(innerPadding)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Accounts(
    navController: NavController,
    accounts: List<AccountData>,
    viewModel: AccountsViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    var openBottomSheet by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf<AccountData?>(null) }
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.background(BackgroundPrimary)
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.app_name), style = title, maxLines = 1)
            Icon(
                modifier = Modifier
                    .height(32.dp)
                    .clickable { navController.navigate(NavRoutes.AddAccount.route) },
                painter = painterResource(R.drawable.ic_add_32dp),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        LazyColumn {
            items(accounts, key = { it.id }) { account ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        when (it) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                openDialog.value = true
                                selectedAccount = account
                                return@rememberSwipeToDismissBoxState false
                            }

                            SwipeToDismissBoxValue.EndToStart -> {}

                            SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                        }
                        return@rememberSwipeToDismissBoxState true
                    },
                    positionalThreshold = { it * .25f }
                )
                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier,
                    enableDismissFromEndToStart = false,
                    backgroundContent = { DismissBackground(dismissState) },
                    content = {
                        Account(account, onClick = {
                            selectedAccount = account
                            openBottomSheet = true
                            scope.launch { bottomSheetState.expand() }
                        })
                    })

            }
        }
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch { bottomSheetState.hide() }
                openBottomSheet = false
            },
            sheetState = bottomSheetState,
            dragHandle = null
        ) {
            AccountBottomSheetContent(selectedAccount, viewModel) {
                scope.launch { bottomSheetState.hide() }
                openBottomSheet = false
            }
        }
    }
    if (openDialog.value) {
        ConfirmDismissAlertDialog(
            title = { },
            description = { Text(stringResource(R.string.delete_account_description, selectedAccount?.accountName ?: "")) },
            confirmBtnText = stringResource(R.string.delete),
            onDismiss = {
                openDialog.value = false
            },
            onConfirm = {
                openDialog.value = false
                selectedAccount?.let { viewModel.deleteAccount(it) }
                context.toast(context.stringResource(R.string.account_deleted))
            }
        )
    }
}

@Composable
private fun AccountBottomSheetContent(
    selectedAccount: AccountData?,
    viewModel: AccountsViewModel,
    onCloseBtnClick: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    var timeLeft by remember { mutableLongStateOf(TOTP.getTimeRemaining()) }
    var authCode by remember { mutableStateOf("") }

    LaunchedEffect(timeLeft) {
        while (true) {
            timeLeft = TOTP.getTimeRemaining()
            selectedAccount?.let { account ->
                authCode = viewModel.getTOTPCode(account.secretKey)
            }
            delay(1000L)
        }
    }

    Box(modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Column {
            Icon(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.End)
                    .clickable {
                        onCloseBtnClick()
                    },
                painter = painterResource(R.drawable.ic_close_24dp),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.key_works),
                style = title1
            )
            Text(
                text = stringResource(R.string.time_left, timeLeft),
                style = body1,
                color = if (timeLeft < 11) Red else Green,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 14.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = authCode,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.key)) },
                readOnly = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(authCode))
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.copy_key)
            )
        }
    }
}

@Composable
private fun Account(
    account: AccountData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(4.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .background(White)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = account.accountName, style = customTextStyle, maxLines = 1)
            }
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    modifier = Modifier.height(24.dp),
                    painter = painterResource(R.drawable.ic_arrow_right_16dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> BackgroundDelete
        SwipeToDismissBoxValue.EndToStart -> Transparent
        SwipeToDismissBoxValue.Settled -> Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete)
        )
    }
}