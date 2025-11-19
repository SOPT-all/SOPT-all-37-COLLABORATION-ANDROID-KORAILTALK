package org.sopt.korailtalk.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun KorailTalkBasicTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    height: Dp = 36.dp,
    enabled: Boolean = true,
    trailingContent: @Composable () -> Unit = {},
    textStyle: TextStyle = KorailTalkTheme.typography.body.body1R16,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(KorailTalkTheme.colors.white)
            .border(
                width = 1.dp,
                color = KorailTalkTheme.colors.gray200,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = value,
            textStyle = textStyle.copy(color = KorailTalkTheme.colors.black),
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enabled = enabled,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            maxLines = maxLines,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (value.isEmpty()) { // place holder
                            Text(
                                text = placeholder,
                                style = textStyle,
                                color = KorailTalkTheme.colors.gray400
                            )
                        }
                        innerTextField()
                    }
                    trailingContent()
                }
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun BasicTextFieldPreview() {
    KORAILTALKTheme {
        var text by remember { mutableStateOf("") }

        KorailTalkBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = "placeHolder",
            value = text,
            onValueChange = { text = it },
            trailingContent = {},
        )
    }
}