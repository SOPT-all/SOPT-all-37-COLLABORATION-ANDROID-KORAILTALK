package org.sopt.korailtalk.core.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

enum class DialogButtonType {
    PRIMARY, // 파란색 (기본)
    DESTRUCTIVE // 빨간색 (삭제/취소)
}

//버튼 하나
@Composable
fun KorailTalkDialogOneButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    type: DialogButtonType = DialogButtonType.PRIMARY,
) {
    val containerColor = if (type == DialogButtonType.PRIMARY) {
        KorailTalkTheme.colors.primary500
    } else {
        KorailTalkTheme.colors.pointRed
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(size = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = KorailTalkTheme.colors.white
        )
    ) {
        Text(text)
    }
}

//버튼 두개
@Composable
fun KorailTalkDialogTwoButtonRow(
    modifier: Modifier = Modifier,
    dismissText: String,
    onDismiss: () -> Unit,
    confirmText: String,
    onConfirm: () -> Unit,
    confirmType: DialogButtonType = DialogButtonType.PRIMARY
) {
    val (containerColor, contentColor) = if (confirmType == DialogButtonType.PRIMARY) {
        Pair(KorailTalkTheme.colors.primary500, KorailTalkTheme.colors.white)
    } else {
        Pair(KorailTalkTheme.colors.pointRed, KorailTalkTheme.colors.black)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        // 취소 버튼
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = KorailTalkTheme.colors.gray100,
                contentColor = KorailTalkTheme.colors.black
            ),
            shape = RoundedCornerShape(size = 8.dp)
        ) {
            Text(dismissText, color = KorailTalkTheme.colors.black)
        }

        // 확인 버튼
        Button(
            onClick = onConfirm,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            shape = RoundedCornerShape(size = 8.dp)
        ) {
            Text(confirmText)
        }
    }
}


@Preview(name = "버튼 2개 다이얼로그", showBackground = true)
@Composable
private fun CustomDialogPreview_TwoButtons() {
    KORAILTALKTheme {
        KorailTalkBasicDialogContent(
            message = "예약을 취소하시겠습니까?",
            buttons = {
                KorailTalkDialogTwoButtonRow(
                    dismissText = "아니요",
                    onDismiss = { },
                    confirmText = "예",
                    onConfirm = { },
                    confirmType = DialogButtonType.DESTRUCTIVE
                )
            }
        )
    }
}

@Preview(name = "버튼 1개 다이얼로그", showBackground = true)
@Composable
private fun CustomDialogPreview_OneButton() {
    KORAILTALKTheme {
        KorailTalkBasicDialogContent(
            message = "해당 카테고리를 삭제할까요?",
            buttons = {
                KorailTalkDialogOneButton(
                    text = "확인",
                    onClick = { },
                    type = DialogButtonType.PRIMARY
                )
            }
        )
    }
}
