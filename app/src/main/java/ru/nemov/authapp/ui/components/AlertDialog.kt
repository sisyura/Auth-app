package ru.nemov.authapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.nemov.authapp.R

@Composable
fun ConfirmDismissAlertDialog(
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    confirmBtnText: String = stringResource(R.string.ok),
    cancelBtnText: String = stringResource(R.string.cancel),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = title,
        onDismissRequest = { onDismiss() },
        text = description,
        confirmButton = {
            CustomButton(
                onClick = { onConfirm() },
                text = confirmBtnText
            )
        },
        dismissButton = {
            CustomButton(
                onClick = { onDismiss() },
                text = cancelBtnText,
                buttonColor = BtnColor.TransparentColor
            )
        }
    )
}