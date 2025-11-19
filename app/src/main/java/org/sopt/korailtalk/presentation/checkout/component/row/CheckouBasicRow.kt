package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            CheckboxDropDownRow(
                title = "할인 쿠폰",
                onClick = {},
                placeholder = "적용할 쿠폰 선택",
                modifier = Modifier.fillMaxWidth()
            )

            CheckboxTextFieldRow(
                title = "보훈 번호",
                placeholder = "보훈 번호 9자리",
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}