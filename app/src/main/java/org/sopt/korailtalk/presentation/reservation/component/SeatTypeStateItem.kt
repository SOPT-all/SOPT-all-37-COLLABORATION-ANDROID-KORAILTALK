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
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme.colors
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme.typography
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType

/**
 * 좌석 타입과 상태를 표시하는 컴포넌트
 * @param seatType 좌석 타입 (일반/특실)
 * @param status 좌석 상태 (예매가능/매진임박/매진)
 */
@Composable
fun SeatTypeStateItem(
    seatType: SeatType,
    status: SeatStatusType,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 좌석 타입 라벨 (일반/특)
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
                text = seatType.label,  // enum의 label 프로퍼티 직접 사용
                style = typography.cap.cap2R12,
                color = colors.black
            )
        }

        // 좌석 상태 텍스트 (예매가능/매진임박/매진)
        Text(
            text = status.text,  // enum의 text 프로퍼티 직접 사용
            style = typography.cap.cap1M12,
            color = if (status.isUrgent) colors.pointRed
            else colors.gray400
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SeatTypeStateItemPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        SeatTypeStateItem(
            seatType = SeatType.NORMAL,
            status = SeatStatusType.AVAILABLE,
        )

        SeatTypeStateItem(
            seatType = SeatType.PREMIUM,
            status = SeatStatusType.AVAILABLE,
        )

        SeatTypeStateItem(
            seatType = SeatType.NORMAL,
            status = SeatStatusType.ALMOST_SOLD_OUT,
        )

        SeatTypeStateItem(
            seatType = SeatType.PREMIUM,
            status = SeatStatusType.SOLD_OUT,
        )
    }
}