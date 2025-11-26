package org.sopt.korailtalk.presentation.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.type.SeatType

/**
 * 좌석 선택 아이템
 * @param seatType 좌석 타입
 * @param price 가격
 * @param isSelected 선택 여부
 * @param isEnabled 활성화 여부 (매진이 아닌 경우)
 * @param onClick 클릭 콜백
 */
@Composable
internal fun SeatSelectionItem(
    seatType: SeatType,
    price: Int,
    isSelected: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 배경색: 비활성(매진) -> gray200, 선택 -> primary200, 기본 -> white
    val backgroundColor = when {
        !isEnabled -> KorailTalkTheme.colors.gray200
        isSelected -> KorailTalkTheme.colors.primary200
        else -> KorailTalkTheme.colors.white
    }

    // 테두리색: 비활성(매진) -> gray200, 선택 -> primary400, 기본 -> gray200
    val borderColor = when {
        !isEnabled -> KorailTalkTheme.colors.gray200
        isSelected -> KorailTalkTheme.colors.primary400
        else -> KorailTalkTheme.colors.gray200
    }

    // 텍스트색: 비활성(매진) -> gray300, 선택 -> primary400, 기본 -> black
    val textColor = when {
        !isEnabled -> KorailTalkTheme.colors.gray300
        isSelected -> KorailTalkTheme.colors.primary400
        else -> KorailTalkTheme.colors.black
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable(
                enabled = isEnabled,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = seatType.label,
            style = KorailTalkTheme.typography.body.body1R16,
            color = textColor
        )
        Text(
            text = "${String.format("%,d", price)}원",
            style = KorailTalkTheme.typography.subtitle.sub3M16,
            color = textColor
        )
    }
}