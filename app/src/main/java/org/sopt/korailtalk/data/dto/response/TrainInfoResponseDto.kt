package org.sopt.korailtalk.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainInfoResponseDto(
    @SerialName("startAt")
    val startAt: String,
    @SerialName("arriveAt")
    val arriveAt: String,
    @SerialName("type")
    val type: String,
    @SerialName("trainNumber")
    val trainNumber: Int,
    @SerialName("seatType")
    val seatType: String,
    @SerialName("price")
    val price: Int,
    @SerialName("reservationId")
    val reservationId: Long
)