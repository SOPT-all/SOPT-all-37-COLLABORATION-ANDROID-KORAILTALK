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

/**
 * 열차의 매진 여부를 판별
 * 일반석이 매진이고, 특실이 없거나 특실도 매진인 경우 매진으로 판단
 * @return 매진 여부
 */
private fun DomainTrainItem.isSoldOut(): Boolean {
    return normalSeat.status == SeatStatusType.SOLD_OUT &&
            (premiumSeat?.status == SeatStatusType.SOLD_OUT || premiumSeat == null)
}

/**
 * 열차 예약 카드 컴포넌트
 * @param trainItem 열차 정보
 * @param selectedSeatType 선택된 좌석 타입 ("전체", "일반실", "특실")
 * @param modifier Modifier
 */
@Composable
fun ReservationCard(
    trainItem: DomainTrainItem,
    selectedSeatType: String = "전체",
    modifier: Modifier = Modifier
) {
    val colors = LocalKorailTalkColorsProvider.current
    val typography = LocalKorailTalkTypographyProvider.current

    val isSoldOut = trainItem.isSoldOut()  // 확장 함수 사용

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSoldOut) colors.gray100 else colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = colors.gray200,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 열차 종류 및 번호
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

        // 출발/도착 시간 및 소요시간
        Row(
            verticalAlignment = Alignment.CenterVertically
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
                text = "${trainItem.durationMinutes / 60}시간 ${trainItem.durationMinutes % 60}분",
                style = typography.body.body4R14,
                color = colors.gray400
            )
        }

        // 좌석 정보 (매진이 아닐 때만 표시)
        if (!isSoldOut) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (selectedSeatType) {
                    "일반실" -> {
                        // 일반실만 표시
                        SeatTypeStateItem(
                            seatType = trainItem.normalSeat.type,
                            status = trainItem.normalSeat.status,
                        )
                    }
                    "특실" -> {
                        // 특실만 표시
                        trainItem.premiumSeat?.let { premium ->
                            SeatTypeStateItem(
                                seatType = premium.type,
                                status = premium.status,
                            )
                        }
                    }
                    else -> {
                        // 전체: 모두 표시
                        SeatTypeStateItem(
                            seatType = trainItem.normalSeat.type,
                            status = trainItem.normalSeat.status,
                        )
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
                trainId = 1,
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
                trainId = 2,
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