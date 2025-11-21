package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.designsystem.component.textfield.KorailTalkBasicTextField

@Composable
fun CheckoutTextFieldRow(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    CheckoutBasicRow(
        title = title,
        modifier = modifier,
        content = {
            KorailTalkBasicTextField(
                placeholder = placeholder,
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
        }
    )
}