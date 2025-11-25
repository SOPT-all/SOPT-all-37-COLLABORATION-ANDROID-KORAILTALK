package org.sopt.korailtalk.domain.type

enum class SeatStatusType(val isUrgent: Boolean) {
        AVAILABLE(false),          // 예매가능 - 긴급 아님
        ALMOST_SOLD_OUT(true),     // 매진임박 - 긴급!
        SOLD_OUT(false);           // 매진 - 긴급 아님

        companion object {
                fun from(value: String): SeatStatusType {
                        return when (value) {
                                "예매가능" -> AVAILABLE
                                "매진임박" -> ALMOST_SOLD_OUT
                                "매진" -> SOLD_OUT
                                else -> AVAILABLE  // 기본값
                        }
                }
        }
}