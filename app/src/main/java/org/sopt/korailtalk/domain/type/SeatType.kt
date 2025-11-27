package org.sopt.korailtalk.domain.type

/**
 * 좌석 타입을 나타내는 Enum
 * @property label 화면에 표시될 좌석 타입 라벨
 */
enum class SeatType(
    val label: String
) {
    NORMAL("일반"),
    PREMIUM("특")
}