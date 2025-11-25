package org.sopt.korailtalk.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class HomeBasicInfoResponseDto (
    @SerialName("origin")
    val origin: String,
    @SerialName("destination")
    val destination: String
)
