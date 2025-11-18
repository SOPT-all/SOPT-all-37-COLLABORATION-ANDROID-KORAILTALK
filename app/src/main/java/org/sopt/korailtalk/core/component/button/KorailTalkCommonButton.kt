package org.sopt.korailtalk.core.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.common.util.extension.pressedClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun KorailTalkBasicButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val currentEnabled by rememberUpdatedState(enabled)

    Box(
        modifier = modifier
            .background(
                color = if (!enabled) {
                    KorailTalkTheme.colors.gray200
                } else {
                    KorailTalkTheme.colors.primary700
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .pressedClickable(
                changePressed = {
                    // Pressed 상태가 없음
                },
                onClick = {
                    if (currentEnabled) {
                        onClick()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = KorailTalkTheme.typography.headline.head4M18,
            color = if (enabled) KorailTalkTheme.colors.white else KorailTalkTheme.colors.gray300
        )
    }
}

@Preview
@Composable
private fun KorailTalkBasicButtonPreview() {
    KorailTalkBasicButton(
        buttonText = "Text in button",
        onClick = {}
    )
}