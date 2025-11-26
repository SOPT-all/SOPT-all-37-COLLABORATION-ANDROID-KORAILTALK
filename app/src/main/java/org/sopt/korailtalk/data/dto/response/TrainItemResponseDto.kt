package org.sopt.korailtalk.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TrainItemResponseDto(

    @SerialName("type")
    val type: String,

    @SerialName("trailNumber")
    val trailNumber: String,

    @SerialName("startAt")
    val startAt: String,

    @SerialName("arriveAt")
    val arriveAt: String,

    @SerialName("duration")
    val duration: Int,

    @SerialName("normalSeatStatus")
    val normalSeatStatus: String,

    @SerialName("premiumSeatStatus")
    val premiumSeatStatus: String?,

    @SerialName("normalSeatPrice")
    val normalSeatPrice: Int,

    @SerialName("premiumSeatPrice")
    val premiumSeatPrice: Int?
)


@Serializable
data class TrainDataResponseDto(
    @SerialName("origin")
    val origin: String,

    @SerialName("destination")
    val destination: String,

    @SerialName("totalTrains")
    val totalTrains: Int,

    @SerialName("nextCursor")
    val nextCursor: String,

    @SerialName("trainList")
    val trainList: List<TrainItemResponseDto>
)