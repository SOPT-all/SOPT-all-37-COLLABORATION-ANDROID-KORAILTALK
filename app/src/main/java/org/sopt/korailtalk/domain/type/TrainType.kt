package org.sopt.korailtalk.domain.type

import androidx.compose.ui.graphics.Color
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
    val enabledColor: Color
) {
    KTX("KTX", primary_700),                      // KTX 열차
    SRT("SRT", secondary_p_400),                  // SRT 열차
    ITX_SAEMAEUL("ITX-새마을", secondary_m_400),  // ITX-새마을 열차
    MUGUNGHWA("무궁화", point_orange),            // 무궁화호
    ITX_MAEUM("ITX-마음", secondary_m_400)        // ITX-마음 열차
}