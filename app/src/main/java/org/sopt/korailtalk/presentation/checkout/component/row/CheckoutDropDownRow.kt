package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.designsystem.component.dropdown.KorailTalkTextDropDown

@Composable
fun CheckoutDropDownRow(
    title: String,
    onClick: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    selected: String = ""
) {
    CheckoutBasicRow(
        title = title,
        modifier = modifier,
        content = {
            KorailTalkTextDropDown(
                onClick = onClick,
                placeholder = placeholder,
                selected = selected
            )
        }
    )
}