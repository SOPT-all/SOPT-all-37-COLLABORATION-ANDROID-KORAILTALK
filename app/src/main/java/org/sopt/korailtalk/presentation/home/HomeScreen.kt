package org.sopt.korailtalk.presentation.home

import android.widget.Toast
import org.sopt.korailtalk.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.KorailTalkBasicTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.items
import org.sopt.korailtalk.presentation.home.component.CheckTrainCard
import org.sopt.korailtalk.presentation.home.component.EtcGridCards

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToReservation: (String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getHomeBasicInfo()
    }

    HomeScreen(
        paddingValues = paddingValues,
        uiState = uiState,
        onSwapClick = { viewModel.swapStations() },
        navigateToReservation = navigateToReservation
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    uiState: HomeUiState,
    onSwapClick: () -> Unit,
    navigateToReservation: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {


    Column(
        modifier
            .padding(paddingValues)
            .background(KorailTalkTheme.colors.gray50)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        KorailTalkBasicTopAppBar(
            navigationIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.img_korail_logo),
                    contentDescription = "코레일 로고",
                    tint = KorailTalkTheme.colors.white

                )
            },
            actions = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_translate),
                    contentDescription = null,
                    tint = KorailTalkTheme.colors.white
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_hamburger),
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
        CheckTrainCard(
            startStation = uiState.startStation,
            endStation = uiState.endStation,
            onSwapClick = onSwapClick,
            onReservationClick = {
                navigateToReservation(
                    uiState.startStation,
                    uiState.endStation
                )
            },
            Modifier.padding(horizontal = 16.dp, vertical = 20.dp)

        )
        val items = items.toPersistentList()
        EtcGridCards(
            items = items,
            Modifier.padding(horizontal = 16.dp)
        )
    }
}


@DefaultPreview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        paddingValues = PaddingValues(),
        uiState = HomeUiState(startStation = "서울", endStation = "부산"),
        onSwapClick = {},
        navigateToReservation = { _, _ -> }
    )
}
