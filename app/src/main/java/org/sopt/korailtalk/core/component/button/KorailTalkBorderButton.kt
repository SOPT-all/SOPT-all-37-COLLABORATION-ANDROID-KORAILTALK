package org.sopt.korailtalk.core.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.core.util.pressedClickable

@Composable
fun KorailTalkBorderButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = KorailTalkTheme.colors.gray200,
    backgroundColor: Color = KorailTalkTheme.colors.white
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
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
            style = KorailTalkTheme.typography.headline.head4M18,
            color = KorailTalkTheme.colors.black
        )
    }
}

@Preview
@Composable
fun KorailTalkBorderButtonPreview() {
    KorailTalkBorderButton(buttonText = "Text in Button", onClick = {})
}


