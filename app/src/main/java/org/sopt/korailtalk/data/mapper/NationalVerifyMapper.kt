package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.request.NationalVerifyRequestDto
import org.sopt.korailtalk.domain.model.DomainNationalVerify

fun DomainNationalVerify.toDto() : NationalVerifyRequestDto {
    return NationalVerifyRequestDto(
        nationalId = this.nationalId,
        password = this.password,
        birthdate = this.birthDate
    )
}
