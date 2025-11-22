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


@Composable
fun SeatTypeItem(
    seatType: String,
    status: String? = null,
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
                text = seatType,
                style = typography.cap.cap2R12,
                color = colors.black
            )
        }

        if (status != null) {
            Text(
                text = status,
                style = typography.cap.cap1M12,
                color = if (isUrgent) colors.pointRed else colors.gray400
            )
        }
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
            seatType = "일반",
            status = "예매가능",
            isUrgent = false
        )

        SeatTypeItem(
            seatType = "특",
            status = "예매가능",
            isUrgent = false
        )

        SeatTypeItem(
            seatType = "일반",
            status = "매진임박",
            isUrgent = true
        )

        SeatTypeItem(
            seatType = "특",
            status = "매진임박",
            isUrgent = true
        )
    }
}