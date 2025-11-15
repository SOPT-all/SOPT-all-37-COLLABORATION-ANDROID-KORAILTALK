package org.sopt.korailtalk.presentation.reservation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview

@Composable
fun ReservationScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            "Reservation"
        )
    }
}

@DefaultPreview
@Composable
private fun ReservationScreenPreview() {
    ReservationScreen()
}