package org.sopt.korailtalk.presentation.others

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview

@Composable
fun TicketRoute(
    paddingValues: PaddingValues
) {
    TicketScreen(
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
private fun TicketScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            "Ticket"
        )
    }
}

@DefaultPreview
@Composable
private fun TicketScreenPreview() {
    TicketScreen()
}