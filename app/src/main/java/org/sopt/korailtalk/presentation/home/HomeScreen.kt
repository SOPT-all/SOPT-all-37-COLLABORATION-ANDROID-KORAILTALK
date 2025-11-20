package org.sopt.korailtalk.presentation.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.component.button.KorailTalkBasicButton
import org.sopt.korailtalk.core.designsystem.component.topappbar.KorailTalkBasicTopAppBar
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToReservation: () -> Unit
) {
//    HomeScreenContent(
//        modifier = Modifier.padding(paddingValues),
//        //데이터 전달
//        startStation = startStation,
//        endStation = endStation,
//        dateText = dateText,
//        //이벤트 콜백 전달
//        onReservationClick = navigateToReservation
//
//    )
}

//UI 분리
@Composable
private fun HomeScreenContent(
    startStation: String,
    endStation: String,
    dateText: String,
    onReservationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .background(KorailTalkTheme.colors.white)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal=20.dp,vertical=16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        KorailTalkBasicTopAppBar()
        Text(
            "어디로 갈까요, 민주 님?",
            style = KorailTalkTheme.typography.headline.head4M18,
            color = KorailTalkTheme.colors.primary400
        )
        Spacer(Modifier.height(24.dp))
        //출발도착날짜인원조회 박스
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Modifier
                .shadow(
                    elevation = 12.dp,
                    spotColor = KorailTalkTheme.colors.black,
                    ambientColor = KorailTalkTheme.colors.black
                )
                .width(328.dp)
                .height(324.dp)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 24.dp)
            //출발
            Row {
                Text(
                    text = "출발",
                    style = KorailTalkTheme.typography.body.body2M15,
                    color = KorailTalkTheme.colors.gray400
                )

            }
            //도착
            Row {

            }
            //날짜
            Row {

            }
            //인원
            Row {

            }
            //버튼
            KorailTalkBasicButton(
                buttonText = "열차조회",
                onClick = onReservationClick,
                Modifier.noRippleClickable(onClick = onReservationClick)
            )
        }
    }
}


@DefaultPreview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        startStation = "서울",
        endStation = "부산",
        dateText = "11.10 (월) · 14시 이후",
        onReservationClick = {}
    )
}
