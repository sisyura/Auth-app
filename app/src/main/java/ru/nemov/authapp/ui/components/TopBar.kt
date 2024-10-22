package ru.nemov.authapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.nemov.authapp.R
import ru.nemov.authapp.ui.theme.White

/**
 * На будущее, чтобы была возможность в title использовать не только String
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    titleText: @Composable () -> Unit,
    navigationIcon: Painter = painterResource(R.drawable.ic_arrow_left_black_16dp),
    navigationIconDescription: String = "Back",
    onClickNavigation: () -> Unit,
    topBarColor: Color = White,
    actionIcon: Painter? = null,
    actionIconContentDescription: String = "",
    onActionClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                titleText()
            }
        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onClickNavigation() }
                ) {
                    Icon(
                        painter = navigationIcon,
                        contentDescription = navigationIconDescription,
                        tint = Color.Unspecified
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = topBarColor,
        ),
        actions = {
            if (actionIcon != null) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onActionClick() }
                    ) {
                        Icon(
                            painter = actionIcon,
                            contentDescription = actionIconContentDescription,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        },
        modifier = modifier.height(56.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    titleText: String = "",
    navigationIcon: Painter = painterResource(R.drawable.ic_arrow_left_black_16dp),
    navigationIconDescription: String = "Back",
    onClickNavigation: () -> Unit,
    topBarColor: Color = White,
    actionIcon: Painter? = null,
    actionIconContentDescription: String = "",
    onActionClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = titleText)
                    }
        },
        navigationIcon = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onClickNavigation() }
                ) {
                    Icon(
                        painter = navigationIcon,
                        contentDescription = navigationIconDescription,
                        tint = Color.Unspecified
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = topBarColor,
        ),
        actions = {
            if (actionIcon != null) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onActionClick() }
                    ) {
                        Icon(
                            painter = actionIcon,
                            contentDescription = actionIconContentDescription,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        },
        modifier = modifier.height(56.dp)
    )
}