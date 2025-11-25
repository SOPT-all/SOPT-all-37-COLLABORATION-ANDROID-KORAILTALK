package org.sopt.korailtalk.presentation.home

import org.sopt.korailtalk.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.toPersistentList
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.KorailTalkBasicTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.items
import org.sopt.korailtalk.presentation.home.component.CheckTrainCard
import org.sopt.korailtalk.presentation.home.component.EtcGridCards

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToReservation: (String, String) -> Unit
) {
    HomeScreenContent(
        modifier = Modifier.padding(paddingValues),
        navigateToReservation = navigateToReservation

    )
}

@Composable
private fun HomeScreenContent(
    navigateToReservation: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier
            .background(KorailTalkTheme.colors.gray50)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        KorailTalkBasicTopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.img_korail_logo),
                    contentDescription = null,
                    tint = KorailTalkTheme.colors.white
                )
            },
            actions = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_translate),
                    contentDescription = null,
                    tint = KorailTalkTheme.colors.white
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger),
                    contentDescription = null,
                    tint = KorailTalkTheme.colors.white
                )
            }
        )
        Row(
            Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp)
        ) {
            Text(
                "어디로 갈까요,",
                style = KorailTalkTheme.typography.headline.head4M18,
                color = KorailTalkTheme.colors.primary700,
            )
            Text(
                "민주 님?",
                style = KorailTalkTheme.typography.headline.head3Sb18,
                color = KorailTalkTheme.colors.primary700,
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            CheckTrainCard(
                startStation = uiState.startStation,
                endStation = uiState.endStation,
                onSwapClick = { viewModel.swapStations() },
                onReservationClick = {
                    navigateToReservation(
                        uiState.startStation,
                        uiState.endStation
                    )
                }
            )
        }
        Spacer(Modifier.height(24.dp))
        val items = items.toPersistentList()
        EtcGridCards(items = items)

    }
}


@DefaultPreview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        navigateToReservation = { _, _ -> }
    )
}
