package org.sopt.korailtalk.domain.model

import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

data class DomainTrainInfoRequest(
    val seatType: SeatType
)

data class DomainTrainInfo(
    val startAt: String,
    val arriveAt: String,
    val type: TrainType,
    val trainNumber: Int,
    val price: Int,
    val seatType: SeatType,
    val reservationId: Long,
    val coupons: List<DomainCouponData>
)

data class DomainCouponData(
    val name: String,
    val discountRate: Int
)
