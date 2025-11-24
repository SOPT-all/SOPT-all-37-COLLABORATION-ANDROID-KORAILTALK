package org.sopt.korailtalk.presentation.home

import org.sopt.korailtalk.R
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.topappbar.KorailTalkBasicTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.presentation.home.component.CheckTrainCard
import org.sopt.korailtalk.presentation.home.component.GridItemData
import org.sopt.korailtalk.presentation.home.component.IconGridScreen

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToReservation: (String,String) -> Unit
) {
    HomeScreenContent(
        modifier = Modifier.padding(paddingValues),
        navigateToReservation = navigateToReservation

    )
}

val items = listOf(
    GridItemData(R.drawable.ic_navigation, "길안내"),
    GridItemData(R.drawable.ic_location, "열차위치"),
    GridItemData(R.drawable.ic_parking, "주차"),
    GridItemData(R.drawable.ic_bus, "공항버스"),
    GridItemData(R.drawable.ic_car, "렌터카"),
    GridItemData(R.drawable.ic_car_sharing, "카셰어링"),
    GridItemData(R.drawable.ic_carrier, "짐배송"),
    GridItemData(R.drawable.ic_bakery, "카페&빵"),
    GridItemData(R.drawable.ic_ticket, "레저이용권"),
    GridItemData(R.drawable.ic_taxi, "관광택시")
)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToReservation: (String,String) -> Unit
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
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.img_korail_logo),
                    contentDescription = null,
                )
            },
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_translate),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            })
        Row(
            Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp)
        ) {
            Text(
                "어디로 갈까요,민주 님?",
                style = KorailTalkTheme.typography.headline.head4M18,
                color = KorailTalkTheme.colors.primary700,
            )
        }
        Box(
             Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            CheckTrainCard(
                startStation = uiState.startStation,
                endStation = uiState.endStation,
                onSwapClick = { viewModel.swapStations() }, // 뷰모델의 함수 호출
                onReservationClick = { navigateToReservation(uiState.startStation, uiState.endStation) }
            )
        }
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconGridScreen(items = items)
        }

    }


}


@DefaultPreview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        navigateToReservation = {_,_->}
    )
}
