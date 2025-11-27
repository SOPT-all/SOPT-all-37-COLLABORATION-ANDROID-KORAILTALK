package org.sopt.korailtalk.core.designsystem.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.designsystem.component.button.DialogSingleButton

@Composable
fun ConfirmDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    message: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
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