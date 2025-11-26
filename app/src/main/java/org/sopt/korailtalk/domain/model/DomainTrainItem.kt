package org.sopt.korailtalk.domain.model

import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

data class DomainTrainItem(
    val type: TrainType,
    val trainNumber: String,
    val departureTime: String,
    val arrivalTime: String,
    val durationMinutes: Int,
    val normalSeat: SeatInfo,
    val premiumSeat: SeatInfo?  // 특실 없는 열차는 null
)

data class SeatInfo(
    val type: SeatType,
    val status: SeatStatusType,
    val price: Int
)

data class TrainSearchResult(
    val origin: String,
    val destination: String,
    val totalTrains: Int,
    val trains: List<DomainTrainItem>,
    val nextCursor: String?
)