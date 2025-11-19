package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme

@Composable
fun CheckboxBasicRow(
    title: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.width(96.dp)
        )
        Row(
            modifier = Modifier.weight(1f)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckboxRowPreview() {
    KORAILTALKTheme {
        var couponText by remember { mutableStateOf("") }
        var numberText by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            CheckboxDropDownRow(
                title = "할인 쿠폰",
                onClick = {}, // 바텀시트 노출 등 작업
                placeholder = "적용할 쿠폰 선택",
                selected = couponText,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "할인쿠폰 1",
                    modifier = Modifier
                        .clickable { couponText = "할인쿠폰 1" }
                )

                Text(
                    text = "할인쿠폰 2",
                    modifier = Modifier
                        .clickable { couponText = "할인쿠폰 2" }
                )
            }

            CheckboxTextFieldRow(
                title = "보훈 번호",
                placeholder = "보훈 번호 9자리",
                value = numberText,
                onValueChange = { numberText = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}