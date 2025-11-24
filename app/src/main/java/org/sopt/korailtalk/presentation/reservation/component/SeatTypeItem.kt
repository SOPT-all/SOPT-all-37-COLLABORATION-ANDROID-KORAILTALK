package org.sopt.korailtalk.presentation.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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


@Composable
fun SeatTypeItem(
    seatType: SeatType,
    status: SeatStatusType,
    isUrgent: Boolean = false,
) {
    val colors = LocalKorailTalkColorsProvider.current
    val typography = LocalKorailTalkTypographyProvider.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(37.dp)
                .height(20.dp)
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (seatType) { //
                    SeatType.NORMAL -> "일반"
                    SeatType.PREMIUM -> "특"
                },
                style = typography.cap.cap2R12,
                color = colors.black
            )
        }

            Text(
                text = when (status){
                    SeatStatusType.ALMOST_SOLD_OUT -> "매진임박"
                    SeatStatusType.AVAILABLE -> "예매가능"
                    SeatStatusType.SOLD_OUT -> "매진"
                },
                style = typography.cap.cap1M12,
                color = if (isUrgent) colors.pointRed else colors.gray400
            )
        }
    }

@Composable
@Preview(showBackground = true)
fun SeatTypeItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        SeatTypeItem(
            seatType = SeatType.NORMAL,
            status = SeatStatusType.AVAILABLE,
            isUrgent = false
        )

        SeatTypeItem(
            seatType = SeatType.PREMIUM,
            status = SeatStatusType.AVAILABLE,
            isUrgent = false
        )

        SeatTypeItem(
            seatType = SeatType.NORMAL,
            status = SeatStatusType.ALMOST_SOLD_OUT,
            isUrgent = true
        )

        SeatTypeItem(
            seatType = SeatType.PREMIUM,
            status = SeatStatusType.SOLD_OUT,
            isUrgent = true
        )
    }
}