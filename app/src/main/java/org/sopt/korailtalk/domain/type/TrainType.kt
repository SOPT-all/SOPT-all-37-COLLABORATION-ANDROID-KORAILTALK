package org.sopt.korailtalk.domain.type

import androidx.compose.ui.graphics.Color
import org.sopt.korailtalk.core.designsystem.theme.point_orange
import org.sopt.korailtalk.core.designsystem.theme.primary_700
import org.sopt.korailtalk.core.designsystem.theme.secondary_m_400
import org.sopt.korailtalk.core.designsystem.theme.secondary_p_400

enum class TrainType(val displayName: String, val enabledColor: Color) {
    KTX("KTX", primary_700),
    SRT("SRT", secondary_p_400),
    ITX_SAEMAEUL("ITX-새마을", secondary_m_400),
    MUGUNGHWA("무궁화", point_orange),
    ITX_MAEUM("ITX-마음", secondary_m_400);

    companion object { // "KTX-C", "예매가능" 같은 문자열을 Enum으로 변환
        fun from(value: String): TrainType {
            return when (value.uppercase()) {
                "KTX", "KTX-C", "KTX-S" -> KTX
                "SRT" -> SRT
                "ITX-SAEMAEUL", "ITX-새마을" -> ITX_SAEMAEUL
                "MUGUNGHWA", "무궁화", "FLOWER" -> MUGUNGHWA
                "ITX-MAEUM", "ITX-마음", "ITX-M" -> ITX_MAEUM
                else -> KTX  // 기본값
            }
        }
    }
}