package org.sopt.korailtalk.core.designsystem.component.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.component.textfield.KorailTalkBasicTextField
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme

@Composable
fun KorailTalkTextDropdown(
    onClick: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    selected: String = ""
) {
    KorailTalkBasicTextField(
        modifier = modifier.noRippleClickable(onClick = onClick), // 컴포넌트 전체 클릭
        enabled = false,
        value = selected,
        onValueChange = {},
        placeholder = placeholder,
        trailingContent = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        },
        maxLines = 1
    )
}

@Preview(showBackground = true)
@Composable
private fun TextDropdownPreview() {
    KORAILTALKTheme {
        var selectedText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KorailTalkTextDropdown(
                onClick = {},
                placeholder = "선택해주세요",
                selected = selectedText,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "옵션 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedText = "옵션 1" }
            )

            Text(
                text = "옵션 2",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedText = "옵션 2" }
            )
        }
    }
}