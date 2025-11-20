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
    ITX_MAEUM("ITX-마음", secondary_m_400)
}