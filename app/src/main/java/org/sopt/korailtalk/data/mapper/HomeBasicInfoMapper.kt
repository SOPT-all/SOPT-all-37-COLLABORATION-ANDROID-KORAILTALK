package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.response.HomeBasicInfoResponseDto
import org.sopt.korailtalk.domain.model.DomainHomeBasicInfo


fun Result<BaseResponse<HomeBasicInfoResponseDto>>.toModel(): Result<DomainHomeBasicInfo> {
    return this.mapCatching { baseResponse ->
        val dto = baseResponse.data

        DomainHomeBasicInfo(
            origin = dto.origin,
            destination = dto.destination
        )
    }
}
