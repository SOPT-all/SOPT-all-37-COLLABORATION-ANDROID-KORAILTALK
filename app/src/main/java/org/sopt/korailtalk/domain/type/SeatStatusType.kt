package org.sopt.korailtalk.domain.type

/**
 * 좌석 상태를 나타내는 Enum
 * @property text 화면에 표시될 텍스트
 * @property isUrgent 긴급 상태 여부 (매진임박일 경우 빨간색 표시)
 */
enum class SeatStatusType(
        val text: String,
        val isUrgent: Boolean,
) {
        AVAILABLE("예매가능", false),          // 예매 가능 - 일반 표시
        ALMOST_SOLD_OUT("매진임박", true),     // 매진 임박 - 긴급 표시 (빨간색)
        SOLD_OUT("매진", false);               // 매진 - 일반 표시

        companion object {
                /**
                 * 서버 응답 텍스트를 SeatStatusType으로 변환
                 * @param text 서버에서 받은 상태 텍스트 (예: "예매가능", "매진임박", "매진")
                 * @return 매칭되는 SeatStatusType, 없으면 AVAILABLE 반환
                 */
                fun fromText(text: String): SeatStatusType {
                        return entries.find { it.text == text } ?: AVAILABLE
                }
        }
}