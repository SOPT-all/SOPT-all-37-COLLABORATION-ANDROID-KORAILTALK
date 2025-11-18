package org.sopt.korailtalk.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun KorailTalkBasicDialog(
    onDismissRequest: () -> Unit,
    message: String,
    buttons: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        KorailTalkBasicDialogContent(
            message = message,
            buttons = buttons,
            modifier = Modifier
        )
    }
}


@Composable
private fun KorailTalkBasicDialogContent(
    message: String,
    buttons: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = KorailTalkTheme.colors.white,
        tonalElevation = 6.dp,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(304.dp)
                .height(148.dp)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = message,
                style = KorailTalkTheme.typography.body.body3R15,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxWidth(),
                content = buttons
            )
        }
    }
}



@Preview
@Composable
private fun Preview() {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "다이얼로그 띄우기",
            modifier = Modifier
                .align(Alignment.Center)
                .noRippleClickable {
                    isBottomSheetVisible = true
                }
        )

        if (isBottomSheetVisible) {
            KorailTalkBasicDialog(
                onDismissRequest = { isBottomSheetVisible = false },
                message = "메시지",
                buttons = {
                    // 버튼 추가
                }
            )
        }
    }
}
