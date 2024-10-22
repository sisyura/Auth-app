package ru.nemov.authapp.ui.screen.add_account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanCustomCode
import io.github.g00fy2.quickie.config.BarcodeFormat
import io.github.g00fy2.quickie.config.ScannerConfig
import io.github.g00fy2.quickie.content.QRContent
import ru.nemov.authapp.ui.activity.LocalNavController
import ru.nemov.authapp.ui.components.CustomButton
import ru.nemov.authapp.ui.components.TopAppBar
import ru.nemov.authapp.R
import ru.nemov.authapp.ui.theme.body2
import ru.nemov.authapp.utils.stringResource
import ru.nemov.authapp.utils.toast

@Composable
fun AddAccountMain(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    AddAccountMain(
        navController,
        modifier,
        viewModel = hiltViewModel()
    )
}

@Composable
private fun AddAccountMain(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddAccountViewModel
) {

    val context = LocalContext.current

    val scanner =
        rememberLauncherForActivityResult(contract = ScanCustomCode(), onResult = { qrResult ->
            when (qrResult) {
                is QRResult.QRSuccess -> {
                    when (qrResult.content) {
                        is QRContent.Plain -> {
                            viewModel.changeAccountData(Uri.parse(qrResult.content.rawValue))
                        }

                        else -> {
                            context.toast(
                                context.stringResource(
                                    R.string.qrcode_bad_format
                                )
                            )
                        }
                    }
                }

                is QRResult.QRError -> {
                    context.toast(
                        text = context.stringResource(
                            R.string.qrcode_scan_error,
                            qrResult.exception.javaClass.name
                        )
                    )
                }

                is QRResult.QRMissingPermission -> {
                    context.toast(
                        context.stringResource(
                            R.string.qrcode_no_permission
                        )
                    )
                }

                else -> {
                    // IGNORE
                }
            }
        })

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                titleText = stringResource(R.string.add_account),
                onClickNavigation = { navController.navigateUp() },
                actionIcon = painterResource(R.drawable.ic_qr_22dp),
                actionIconContentDescription = stringResource(R.string.scan_qr),
                onActionClick = {
                    scanner.launch(
                        ScannerConfig.build {
                            setBarcodeFormats(listOf(BarcodeFormat.FORMAT_QR_CODE))
                            setOverlayStringRes(R.string.point_the_camera_at_the_qr_code)
                            setOverlayDrawableRes(null)
                        }
                    )
                }
            )
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AddAccountScreen(viewModel = viewModel, navController)
        }
    }
}

@Composable
private fun AddAccountScreen(
    viewModel: AddAccountViewModel,
    navController: NavController
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.account_name),
            style = body2,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = viewModel.accountName,
            onValueChange = viewModel::onAccountNameChange,
            placeholder = { Text(text = stringResource(R.string.enter_account_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.key),
            style = body2,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = viewModel.key,
            onValueChange = viewModel::onKeyChange,
            placeholder = { Text(text = stringResource(R.string.enter_key)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) ImageVector.vectorResource(id = R.drawable.ic_password_visibility_on) else ImageVector.vectorResource(
                            id = R.drawable.ic_password_visibility_off
                        ),
                        contentDescription = stringResource(R.string.password_visibility),
                        tint = Color.Unspecified
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Text(
            text = "${viewModel.key.length}/32",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            onClick = {
                viewModel.addAccount()
                navController.navigateUp()
            },
            text = stringResource(R.string.add_account),
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.enableAddBtn()
        )
    }
}