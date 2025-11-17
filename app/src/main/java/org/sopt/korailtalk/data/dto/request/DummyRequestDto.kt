package org.sopt.korailtalk.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyRequestDto(
    @SerialName("data")
    val data: String
)