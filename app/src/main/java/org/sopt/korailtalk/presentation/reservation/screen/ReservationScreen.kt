package org.sopt.korailtalk.presentation.reservation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview

@Composable
fun ReservationRoute(
    paddingValues: PaddingValues,
    navigateToCheckout: () -> Unit,
    navigateUp: () -> Unit
) {
    ReservationScreen(
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onTrailItemClick = navigateToCheckout
    )
}

@Composable
private fun ReservationScreen(
    onBackClick: () -> Unit,
    onTrailItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "뒤로가기",
                modifier = Modifier.noRippleClickable(
                    onClick = onBackClick
                )
            )
            Text(
                "열차 아이템 클릭",
                modifier = Modifier
                    .noRippleClickable(
                        onClick = onTrailItemClick
                    )
                    .align(Alignment.Center)
            )
        }
    }
}

@DefaultPreview
@Composable
private fun ReservationScreenPreview() {
    ReservationScreen(
        onBackClick = {},
        onTrailItemClick = {}
    )
}