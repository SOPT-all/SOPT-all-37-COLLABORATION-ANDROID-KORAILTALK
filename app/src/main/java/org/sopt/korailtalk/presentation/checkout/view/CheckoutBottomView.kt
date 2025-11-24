package org.sopt.korailtalk.presentation.checkout.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
            onValueChange = { nationalIdText = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        CheckoutTextFieldRow(
            title = "비밀 번호",
            placeholder = "숫자 4자리",
            value = passwordText,
            onValueChange = { passwordText = it },
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
                        onValueChange = { birthDateText = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    OkButton(
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