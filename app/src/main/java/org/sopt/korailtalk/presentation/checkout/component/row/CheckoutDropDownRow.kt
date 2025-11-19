package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.designsystem.component.dropdown.KorailTalkTextDropdown

@Composable
fun CheckboxDropDownRow(
    title: String,
    onClick: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    selected: String = ""
) {
    CheckboxBasicRow(
        title = title,
        modifier = modifier,
        content = {
            KorailTalkTextDropdown(
                onClick = onClick,
                placeholder = placeholder,
                selected = selected
            )
        }
    )
}