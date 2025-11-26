package org.sopt.korailtalk.core.designsystem.component.button

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
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun OkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String = "확인",
    enabled: Boolean = false
) {
    val currentEnabled by rememberUpdatedState(enabled)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = if (enabled) {
                    KorailTalkTheme.colors.primary300
                } else {
                    KorailTalkTheme.colors.gray300
                },
            )
            .height(36.dp)
            .noRippleClickable(
                enabled = currentEnabled,
                onClick = onClick
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = KorailTalkTheme.typography.body.body1R16,
            color = KorailTalkTheme.colors.white
        )
    }
}

@Preview
@Composable
private fun KorailTalkBasicButtonPreview() {
    OkButton(
        onClick = {}
    )
}
