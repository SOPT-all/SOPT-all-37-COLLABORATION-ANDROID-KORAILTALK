package org.sopt.korailtalk.presentation.checkout.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class NationalIdVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.filter { it.isDigit() }.take(8)
        val formatted = formatNationalIdForRequest(digits)

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = nationalIdOffsetMapping
        )
    }

    private val nationalIdOffsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {

            return when {
                offset <= 2 -> offset // 0,1,2 : 그대로
                else -> offset + 1 // 3~8 : 하이픈 하나만큼 +1
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            val hyphenIndex = 2
            return when {
                offset <= hyphenIndex -> offset // 0,1,2 : 그대로 (2는 '-'지만 커서 기준)
                else -> offset - 1 // 3~8 : 하이픈 하나 빼주기
            }.coerceAtLeast(0)
        }
    }
}

fun formatNationalIdForRequest(digitsOnly: String): String {
    val digits = digitsOnly.filter { it.isDigit() }.take(8)

    return when {
        digits.length <= 2 -> digits // "9", "91"
        else -> digits.substring(0, 2) + "-" + digits.substring(2) // "91026490" -> "91-026490"
    }
}