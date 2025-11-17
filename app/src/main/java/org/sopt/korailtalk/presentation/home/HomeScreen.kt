package org.sopt.korailtalk.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToReservation: () -> Unit
) {
    HomeScreen(
        modifier = Modifier.padding(paddingValues),
        onReservationClick = navigateToReservation
    )
}

@Composable
private fun HomeScreen(
    onReservationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Text(
            "Home",
            modifier = Modifier.noRippleClickable(
                onClick = onReservationClick
            )
        )
    }
}

@DefaultPreview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        onReservationClick = {}
    )
}