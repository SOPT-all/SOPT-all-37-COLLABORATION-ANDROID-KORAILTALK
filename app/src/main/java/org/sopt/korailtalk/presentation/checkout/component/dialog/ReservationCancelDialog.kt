package org.sopt.korailtalk.presentation.checkout.component.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.component.button.DialogCancelButton
import org.sopt.korailtalk.core.designsystem.component.button.DialogConfirmButton
import org.sopt.korailtalk.core.designsystem.component.dialog.KorailTalkBasicDialog

@Composable
fun ReservationCancelDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        KorailTalkBasicDialog(
            onDismissRequest = onDismiss,
            message = "예약을 취소하시겠습니까?",
            modifier = modifier,
            buttons = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    DialogCancelButton(
                        buttonText = "아니오",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DialogConfirmButton(
                        buttonText = "예",
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        )
    }
}