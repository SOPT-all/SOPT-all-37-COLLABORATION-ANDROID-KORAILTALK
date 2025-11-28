package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import org.sopt.korailtalk.core.designsystem.component.textfield.KorailTalkBasicTextField

@Composable
fun CheckoutTextFieldRow(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
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
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation
            )
        }
    )
}