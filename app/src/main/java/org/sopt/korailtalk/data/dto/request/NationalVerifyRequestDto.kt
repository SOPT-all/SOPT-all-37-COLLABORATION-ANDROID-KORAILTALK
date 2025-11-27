package org.sopt.korailtalk.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NationalVerifyRequestDto(
    @SerialName("nationalId")
    val nationalId: String,
    @SerialName("password")
    val password: String,
    @SerialName("birthdate")
    val birthdate: String
)