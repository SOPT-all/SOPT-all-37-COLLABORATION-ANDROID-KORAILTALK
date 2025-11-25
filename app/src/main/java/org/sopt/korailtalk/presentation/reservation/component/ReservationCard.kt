package org.sopt.korailtalk.presentation.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.LocalKorailTalkColorsProvider
import org.sopt.korailtalk.core.designsystem.theme.LocalKorailTalkTypographyProvider
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

@Composable
fun ReservationCard(
    trainItem: DomainTrainItem,  // ReservationInfo -> DomainTrainItem
    modifier: Modifier = Modifier
) {
    val colors = LocalKorailTalkColorsProvider.current
    val typography = LocalKorailTalkTypographyProvider.current

    // 매진 여부 계산
    val isSoldOut = trainItem.normalSeat.status == SeatStatusType.SOLD_OUT &&
            (trainItem.premiumSeat?.status == SeatStatusType.SOLD_OUT || trainItem.premiumSeat == null)

    Column(
        modifier = modifier
            .fillMaxWidth()ㅍ
            .background(
                color = if (isSoldOut) colors.gray100 else colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = colors.gray150,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 열차 정보
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TrainTypeLabel(
                trainType = trainItem.type,
                isEnabled = !isSoldOut
            )
            Text(
                text = trainItem.trainNumber,
                style = typography.body.body4M14,
                color = colors.black
            )
        }

        // 시간 정보
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = trainItem.departureTime,
                    style = typography.headline.head2M20,
                    color = colors.black
                )
                Text(
                    text = "→",
                    style = typography.headline.head2M20,
                    color = colors.gray300
                )
                Text(
                    text = trainItem.arrivalTime,
                    style = typography.headline.head2M20,
                    color = colors.black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${trainItem.durationMinutes}분",  // Int를 문자열로 변환
                style = typography.body.body4M14,
                color = colors.gray400
            )
        }

        // 좌석 정보
        if (!isSoldOut) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 일반석은 항상 표시
                SeatTypeStateItem(
                    seatType = trainItem.normalSeat.type,
                    status = trainItem.normalSeat.status,
                )

                // 특실이 있으면 표시
                trainItem.premiumSeat?.let { premium ->
                    SeatTypeStateItem(
                        seatType = premium.type,
                        status = premium.status,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReservationCardPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 일반 예약 카드
        ReservationCard(
            trainItem = DomainTrainItem(
                type = TrainType.KTX,
                trainNumber = "001",
                departureTime = "05:13",
                arrivalTime = "07:50",
                durationMinutes = 82,
                normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.AVAILABLE, 59000),
                premiumSeat = SeatInfo(SeatType.PREMIUM, SeatStatusType.ALMOST_SOLD_OUT, 83000)
            )
        )

        // 매진 예약 카드
        ReservationCard(
            trainItem = DomainTrainItem(
                type = TrainType.KTX,
                trainNumber = "002",
                departureTime = "06:00",
                arrivalTime = "08:37",
                durationMinutes = 82,
                normalSeat = SeatInfo(SeatType.NORMAL, SeatStatusType.SOLD_OUT, 59000),
                premiumSeat = SeatInfo(SeatType.PREMIUM, SeatStatusType.SOLD_OUT, 83000)
            )
        )
    }
}