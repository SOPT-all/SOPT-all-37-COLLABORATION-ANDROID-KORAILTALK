package org.sopt.korailtalk.core.designsystem.component.dropdown

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
    selected: String = "",
    onSelectChange: (String) -> Unit
) {
    KorailTalkBasicTextField(
        modifier = modifier
            .noRippleClickable(onClick = {
                onClick()
                Log.d("Dropdown", "컴포넌트 클릭!")
            }) // 컴포넌트 전체 클릭
            .fillMaxWidth(),
        value = selected,
        onValueChange = onSelectChange,
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
        KorailTalkTextDropdown(
            onClick = {},
            placeholder = "placeHolder",
            onSelectChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}