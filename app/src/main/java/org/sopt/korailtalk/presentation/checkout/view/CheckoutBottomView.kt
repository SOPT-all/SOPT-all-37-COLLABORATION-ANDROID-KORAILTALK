package org.sopt.korailtalk.presentation.checkout.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import org.sopt.korailtalk.core.designsystem.component.button.OkButton
import org.sopt.korailtalk.core.designsystem.component.checkbox.KorailTalkBasicCheckBox
import org.sopt.korailtalk.core.designsystem.component.textfield.KorailTalkBasicTextField
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutBasicRow
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutDropDownRow
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutSectionRow
import org.sopt.korailtalk.presentation.checkout.component.row.CheckoutTextFieldRow

@Composable
fun CheckoutBottomView(
    modifier: Modifier = Modifier
) { // @nahy-512 작업
    Column(
        modifier = modifier
    ) {
        // 국가유공자 할인
        NationalMeritSection()

        Spacer(Modifier.height(8.dp))

        // 중증보호자 할인
        SevereGuardianSection()

        // 현역병 할인
        ActiveDutySoldierSection()

        // 설명
        GuideContent()
    }
}

@Composable
private fun NationalMeritSection() {
    var nationalIdText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var birthDateText by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    CheckoutSectionRow(
        title = "국가 유공자 할인"
    )

    Spacer(Modifier.height(16.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        CheckoutTextFieldRow(
            title = "보훈 번호",
            placeholder = "보훈 번호 9자리",
            value = nationalIdText,
            onValueChange = {
                if (it.length <= 9) nationalIdText = it
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        CheckoutTextFieldRow(
            title = "비밀 번호",
            placeholder = "숫자 4자리",
            value = passwordText,
            onValueChange = {
                if (it.length <= 4) passwordText = it
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        CheckoutBasicRow(
            title = "생년월일",
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    KorailTalkBasicTextField(
                        placeholder = "생년월일 6자리",
                        value = birthDateText,
                        onValueChange = {
                            if (it.length <= 6) birthDateText = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    OkButton(
                        enabled = checkButtonEnabled(
                            nationalIdText = nationalIdText,
                            passwordText = passwordText,
                            birthDateText = birthDateText
                        ),
                        onClick = {}
                    )
                }
            }
        )

        CheckoutDropDownRow(
            title = "적용 대상",
            placeholder = "적용할 승객 선택",
            onClick = {}, // 바텀시트 노출 등 작업
        )
    }

    Spacer(Modifier.height(8.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        KorailTalkBasicCheckBox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )

        Text(
            text = "개인정보 수집 및 이용 동의",
            style = KorailTalkTheme.typography.body.body1R16,
            color = KorailTalkTheme.colors.black
        )
    }
}

@Composable
private fun SevereGuardianSection() {
    CheckoutSectionRow(
        title = "중증 보호자 할인",
        rightContent = {
            Text(
                text = "적용대상 없음",
                style = KorailTalkTheme.typography.body.body5R13,
                color = KorailTalkTheme.colors.pointRed
            )
        }
    )
}

@Composable
private fun ActiveDutySoldierSection() {
    Column {
        CheckoutSectionRow(
            title = "현역병 할인"
        )

        CheckoutDropDownRow(
            title = "적용 대상",
            placeholder = "적용할 승객 선택",
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun GuideContent() {
    Box(
        modifier = Modifier
            .background(KorailTalkTheme.colors.gray50)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Text(
            text = "· 추가로 할인 가능한 항목이 있으신 경우 할인을 적용해주세요.\n" +
                    "· 추가 할인은 어른/청소년 기준, 예매한 매수만큼 적용할 수 있습니다.\n" +
                    "· 할인승차권 이용시에는 관련 신분증 또는 증명서를 소지하셔야 합니다.\n" +
                    "· 할인 승차권의 할인율은 별도의 공지 없이 변경될 수 있습니다.\n" +
                    "· 할인은 운임에만 적용하고 요금은 미적용(특실/우등실은 운임과 요금으로 구분)되며, 최저운임 이하로 할인하지 않습니다.\n\n" +
                    "· 경증: 장애의 정도가 심하지 않은 장애인 (구 4-6급)\n" +
                    "· 중증: 장애의 정도가 심한 장애인 (구 1-3급)",
            style = KorailTalkTheme.typography.cap.cap2R12.copy(
                lineHeight = 1.5.em
            ),
            color = KorailTalkTheme.colors.gray400
        )
    }
}

// TODO: 뷰모델 로직으로 추가
private fun checkButtonEnabled(
    nationalIdText: String,
    passwordText: String,
    birthDateText: String
): Boolean {
    return nationalIdText.length == 9 &&
            passwordText.length == 4 &&
            birthDateText.length == 6
}

@Preview(showBackground = true)
@Composable
private fun CheckoutBottomViewPreview() {
    KORAILTALKTheme {
        CheckoutBottomView(
            modifier = Modifier
                .fillMaxSize()
                .background(KorailTalkTheme.colors.white)
        )
    }
}