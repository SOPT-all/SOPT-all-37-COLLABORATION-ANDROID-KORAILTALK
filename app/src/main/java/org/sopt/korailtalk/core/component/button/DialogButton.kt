package org.sopt.korailtalk.core.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.core.util.pressedClickable

/**
 * 버튼이 하나만 있는 Dialog 에서 사용
 * */
@Composable
fun DialogSingleButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = KorailTalkTheme.colors.primary500,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .pressedClickable(
                changePressed = {
                    // Pressed 상태가 없음
                },
                onClick = {
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = KorailTalkTheme.typography.body.body2M15,
            color = KorailTalkTheme.colors.white
        )
    }
}

/**
 * 버튼이 두개 있는 Dialog 에서 Confirm으로 사용
 * */
@Composable
fun DialogConfirmButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = KorailTalkTheme.colors.pointRed,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .pressedClickable(
                changePressed = {
                    // Pressed 상태가 없음
                },
                onClick = {
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = KorailTalkTheme.typography.body.body2M15,
            color = KorailTalkTheme.colors.white
        )
    }
}

/**
 * 버튼이 두개 있는 Dialog 에서 Cancel으로 사용
 * */
@Composable
fun DialogCancelButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = KorailTalkTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .pressedClickable(
                changePressed = {
                    // Pressed 상태가 없음
                },
                onClick = {
                    onClick()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = KorailTalkTheme.typography.body.body3R15,
            color = KorailTalkTheme.colors.black
        )
    }
}


@Preview
@Composable
fun DialogSingleButtonPreview() {
    DialogSingleButton(buttonText = "내용 내용 내용", onClick = {})
}

@Preview
@Composable
fun DialogButtonPreview() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        DialogCancelButton(
            buttonText = "취소",
            onClick = {},
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        DialogConfirmButton(
            buttonText = "확인",
            onClick = {},
            modifier = Modifier.weight(1f)
        )
    }
}
