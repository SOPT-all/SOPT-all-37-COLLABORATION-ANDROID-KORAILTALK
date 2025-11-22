package org.sopt.korailtalk.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainInfoRequestDto (
    @SerialName("seatType")
    val seatType: String
)