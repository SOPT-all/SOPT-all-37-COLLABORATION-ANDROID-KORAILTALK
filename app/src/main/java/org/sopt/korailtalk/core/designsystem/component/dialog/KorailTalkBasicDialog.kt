package org.sopt.korailtalk.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.component.button.DialogCancelButton
import org.sopt.korailtalk.core.designsystem.component.button.DialogConfirmButton
import org.sopt.korailtalk.core.designsystem.component.button.DialogSingleButton
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun KorailTalkBasicDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    message: String,
    buttons: @Composable BoxScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        KorailTalkBasicDialogContent(
            message = message,
            buttons = buttons,
            modifier = modifier
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


@Preview(showBackground = true, backgroundColor = 0xFFE5E5E5)
@Composable
private fun KorailTalkDialogPreview() {
    var isCancelDialogVisible by remember { mutableStateOf(false) }
    var isDeleteDialogVisible by remember { mutableStateOf(false) }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "예약 취소 다이얼로그 띄우기 (버튼 2개)",
                    style = KorailTalkTheme.typography.body.body1R16,
                    modifier = Modifier.noRippleClickable {
                        isCancelDialogVisible = true
                    }
                )
                Text(
                    text = "카테고리 삭제 다이얼로그 띄우기 (버튼 1개)",
                    style = KorailTalkTheme.typography.body.body1R16,
                    modifier = Modifier.noRippleClickable {
                        isDeleteDialogVisible = true
                    }
                )
            }

            //예약 취소 다이얼로그 (버튼 2개)
            if (isCancelDialogVisible) {
                KorailTalkBasicDialog(
                    onDismissRequest = { isCancelDialogVisible = false },
                    message = "예약을 취소하시겠습니까?",
                    buttons = {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            DialogCancelButton(
                                buttonText = "아니오",
                                onClick = { isCancelDialogVisible = false },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            DialogConfirmButton(
                                buttonText = "예",
                                onClick = { isCancelDialogVisible = false },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                )
            }

            //카테고리 삭제 다이얼로그 (버튼 1개)
            if (isDeleteDialogVisible) {
                KorailTalkBasicDialog(
                    onDismissRequest = { isDeleteDialogVisible = false },
                    message = "해당 카테고리를 삭제할까요?",
                    buttons = {
                        DialogSingleButton(
                            buttonText = "확인",
                            onClick = { isDeleteDialogVisible = false },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }
    }
