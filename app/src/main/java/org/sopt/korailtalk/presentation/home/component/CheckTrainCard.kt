package org.sopt.korailtalk.presentation.home.component

import org.sopt.korailtalk.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.component.button.KorailTalkBasicButton
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme


@Composable
fun CheckTrainCard(
    startStation: String,       // ViewModel에서 받아올 값
    endStation: String,         // ViewModel에서 받아올 값
    onSwapClick: () -> Unit,    // ViewModel의 함수를 실행할 이벤트
    onReservationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier=modifier
            .shadow(
                elevation = 12.dp,
                spotColor = KorailTalkTheme.colors.black,
                ambientColor = KorailTalkTheme.colors.black
            )
            .background(
                color = KorailTalkTheme.colors.white,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        // 1. 출발/도착 (노선 전환 아이콘 포함)
        DepartureArrivalItem(
            startValue = startStation,
            endValue = endStation,
            onSwapClick = onSwapClick
        )

        // 2. 날짜 및 시간
        CheckTrainItem(
            iconId = R.drawable.ic_calender,
            title = "12. 1  (월)  ·  14시 이후",
            showDivider = true
        )

        // 3. 인원
        CheckTrainItem(
            iconId = R.drawable.ic_person,
            title = "어른 1명",
            showDivider=false
        )

        Spacer(Modifier.height(32.dp)) // 인원과 버튼 사이 간격

        //버튼
        KorailTalkBasicButton(
            buttonText = "열차조회",
            onClick = onReservationClick,
            Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable(onClick = onReservationClick)
                .fillMaxWidth()
        )

    }
}


// 출발/도착 항목 컴포저블
@Composable
private fun DepartureArrivalItem(
    startValue: String,
    endValue: String,
    onSwapClick:()->Unit
) {
    Box(
        Modifier.fillMaxWidth()
    ) {
        Column{

            // 1. 출발 (Start)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "출발",
                    style = KorailTalkTheme.typography.body.body2M15,
                    color = KorailTalkTheme.colors.gray400,
                    modifier = Modifier.width(36.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = startValue,
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black,
                    modifier = Modifier.weight(1f)
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = KorailTalkTheme.colors.gray100, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))


            // 2. 도착 (End)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "도착",
                    style = KorailTalkTheme.typography.body.body2M15,
                    color = KorailTalkTheme.colors.gray400,
                    modifier = Modifier.width(36.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = endValue,
                    style = KorailTalkTheme.typography.headline.head2M20,
                    color = KorailTalkTheme.colors.black,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = KorailTalkTheme.colors.gray100, thickness = 1.dp) // 구분선

        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .offset(x=(-10).dp,y = (-8).dp)
                .background(
                    color = KorailTalkTheme.colors.white,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = KorailTalkTheme.colors.gray150, // 테두리 색상
                    shape = CircleShape
                )
                .noRippleClickable {onSwapClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_exchange),
                contentDescription = "출발/도착 전환",
                modifier = Modifier.size(24.dp) // 내부 아이콘 크기
            )
        }


    }
}


//날짜인원컴포저블
@Composable
private fun CheckTrainItem(
    iconId: Int,
    title: String,
    showDivider: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    )
    {
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id =iconId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = title,
                style = KorailTalkTheme.typography.subtitle.sub3M16,
                color = KorailTalkTheme.colors.black
            )
        }
        if (showDivider) {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = KorailTalkTheme.colors.gray100, // 연한 회색 구분선
                thickness = 1.dp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CheckTrainCardPreview() {
    KORAILTALKTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CheckTrainCard(
                startStation = "서울",
                endStation = "부산",
                onSwapClick = {},
                onReservationClick = {})
        }
    }
}

