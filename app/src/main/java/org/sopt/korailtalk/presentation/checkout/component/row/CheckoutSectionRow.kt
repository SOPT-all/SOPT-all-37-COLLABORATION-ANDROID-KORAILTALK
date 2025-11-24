package org.sopt.korailtalk.presentation.checkout.component.row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun CheckoutSectionRow(
    sectionTitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(KorailTalkTheme.colors.gray50)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(44.dp)
    ) {
        Text(
            text = sectionTitle,
            style = KorailTalkTheme.typography.subtitle.sub3M16,
            color = KorailTalkTheme.colors.black,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutSectionPreview() {
    KORAILTALKTheme {
        CheckoutSectionRow("국가유공자 할인")
    }
}