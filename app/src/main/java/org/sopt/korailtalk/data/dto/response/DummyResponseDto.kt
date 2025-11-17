package org.sopt.korailtalk.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyResponseDto (
    @SerialName("data")
    val data: String
)