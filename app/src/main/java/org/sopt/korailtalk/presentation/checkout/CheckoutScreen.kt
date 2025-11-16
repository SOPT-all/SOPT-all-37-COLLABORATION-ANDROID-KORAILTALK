package org.sopt.korailtalk.presentation.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview

@Composable
fun CheckoutRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    CheckoutScreen(
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onCloseClick = navigateToHome
    )
}

@Composable
private fun CheckoutScreen(
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "뒤로가기",
                modifier = Modifier.noRippleClickable(
                    onClick = onBackClick
                )
            )
            Text(
                "닫기",
                modifier = Modifier.noRippleClickable(
                    onClick = onCloseClick
                )
            )
        }
    }
}

@DefaultPreview
@Composable
private fun CheckoutScreenPreview() {
    CheckoutScreen(
        onBackClick = {},
        onCloseClick = {}
    )
}