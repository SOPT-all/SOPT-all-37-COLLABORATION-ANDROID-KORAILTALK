package org.sopt.korailtalk.presentation.checkout.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.designsystem.component.button.DialogSingleButton
import org.sopt.korailtalk.core.designsystem.component.dialog.KorailTalkBasicDialog

@Composable
fun ConfirmDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    message: String,
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit = onDismiss
) {
    if (isVisible) {
        KorailTalkBasicDialog(
            onDismissRequest = onDismiss,
            message = message,
            modifier = modifier,
            buttons = {
                DialogSingleButton(
                    buttonText = "확인",
                    onClick = onConfirm,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}