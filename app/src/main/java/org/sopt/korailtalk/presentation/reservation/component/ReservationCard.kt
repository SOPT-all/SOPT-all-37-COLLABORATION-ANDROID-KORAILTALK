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
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

// 서버 연동 시에는 매핑 레이어에서 변환예정.
data class ReservationInfo(
    val trainType: TrainType,
    val trainNumber: String,
    val departureTime: String,
    val arrivalTime: String,
    val duration: String,
    val seatTypes: List<SeatInfo> = emptyList(),
    val isSoldOut: Boolean = false
)

data class SeatInfo(
    val type: SeatType,
    val status: SeatStatusType,
    val isUrgent: Boolean = false
)

@Composable
fun ReservationCard(
    reservationInfo: ReservationInfo,
    modifier: Modifier = Modifier
) {
    val colors = LocalKorailTalkColorsProvider.current
    val typography = LocalKorailTalkTypographyProvider.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (reservationInfo.isSoldOut) colors.gray100 else colors.white,
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
                trainType = reservationInfo.trainType,
                isEnabled = !reservationInfo.isSoldOut
            )
            Text(
                text = reservationInfo.trainNumber,
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
                    text = reservationInfo.departureTime,
                    style = typography.headline.head2M20,
                    color = colors.black
                )
                Text(
                    text = "→",
                    style = typography.headline.head2M20,
                    color = colors.gray300
                )
                Text(
                    text = reservationInfo.arrivalTime,
                    style = typography.headline.head2M20,
                    color = colors.black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = reservationInfo.duration,
                style = typography.body.body4M14,
                color = colors.gray400
            )
        }

        // 좌석 정보
        if (!reservationInfo.isSoldOut && reservationInfo.seatTypes.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                reservationInfo.seatTypes.forEach { seatInfo ->
                    SeatTypeStateItem(
                        seatType = seatInfo.type,
                        status = seatInfo.status,
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
            reservationInfo = ReservationInfo(
                trainType = TrainType.KTX,
                trainNumber = "001",
                departureTime = "05:13",
                arrivalTime = "07:50",
                duration = "1시간 22분",
                seatTypes = listOf(
                    SeatInfo(SeatType.NORMAL, SeatStatusType.AVAILABLE),
                    SeatInfo(SeatType.PREMIUM, SeatStatusType.ALMOST_SOLD_OUT, isUrgent = true)
                )
            )
        )

        // 매진 예약 카드
        ReservationCard(
            reservationInfo = ReservationInfo(
                trainType = TrainType.KTX,
                trainNumber = "001",
                departureTime = "05:13",
                arrivalTime = "07:50",
                duration = "1시간 22분",
                seatTypes = emptyList(),
                isSoldOut = true
            )
        )
    }
}