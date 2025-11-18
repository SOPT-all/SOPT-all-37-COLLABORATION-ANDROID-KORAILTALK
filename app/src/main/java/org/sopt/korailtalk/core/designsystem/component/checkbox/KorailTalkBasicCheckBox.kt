package org.sopt.korailtalk.core.designsystem.component.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
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

@Composable
fun KorailTalkBasicCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        imageVector = ImageVector.vectorResource(if (checked) R.drawable.ic_checkbox_on else R.drawable.ic_checkbox_off),
        contentDescription = "CheckBox",
        modifier = modifier
            .noRippleClickable { onCheckedChange(!checked) }
            .size(44.dp)
    )
}

@Preview
@Composable
private fun CheckBoxPreview() {
    var isChecked by remember { mutableStateOf(true) }

    KorailTalkBasicCheckBox(
        checked = isChecked,
        onCheckedChange = { isChecked = it }
    )
}