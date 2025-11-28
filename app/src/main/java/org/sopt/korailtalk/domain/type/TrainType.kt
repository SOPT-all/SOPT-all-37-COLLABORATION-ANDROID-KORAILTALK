package org.sopt.korailtalk.domain.type

import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.sopt.korailtalk.core.designsystem.theme.point_orange
import org.sopt.korailtalk.core.designsystem.theme.primary_700
import org.sopt.korailtalk.core.designsystem.theme.secondary_m_400
import org.sopt.korailtalk.core.designsystem.theme.secondary_p_400

/**
 * 열차 종류를 나타내는 Enum
 * @property displayName 화면에 표시될 열차 이름
 * @property enabledColor 활성화 상태일 때 배경색
 */
enum class TrainType(
    val displayName: String,
    val enabledColor: Color,
    val serverValue: String  // 추가: 서버로 보낼 값
) {
    KTX("KTX", primary_700, "KTX"),
    SRT("SRT", secondary_p_400, "SRT"),
    ITX_SAEMAEUL("ITX-새마을", secondary_m_400, "ITX-N"),
    MUGUNGHWA("무궁화", point_orange, "FLOWER"),  // 서버는 FLOWER로 받음!
    ITX_MAEUM("ITX-마음", secondary_m_400, "ITX-M");  // 서버는 ITX-M으로 받음!
}


enum class TrainFilterType(
    val type: TrainType?,
    val displayName: String
) {
    ALL(
        type = null,
        displayName = "전체"
    ),
    KTX(
        type = TrainType.KTX,
        displayName = "KTX"
    ),
    SRT(
        type = TrainType.SRT,
        displayName = "SRT"
    ),
    MUGUNGHWA(
        type = TrainType.MUGUNGHWA,
        displayName = "무궁화"
    ),
    ITX(
        type = TrainType.ITX_SAEMAEUL, // TODO: 임시
        displayName = "ITX-마음/새마을"
    );

    companion object {
        fun getFilterList(): ImmutableList<TrainFilterType> {
            return entries.toImmutableList()
        }
    }
}