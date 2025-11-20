package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.request.TrainInfoRequestDto
import org.sopt.korailtalk.data.dto.response.TrainInfoResponseDto
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

fun DomainTrainInfoRequest.toDto() : TrainInfoRequestDto {
    return TrainInfoRequestDto(
        seatType = this.seatType.name,
    )
}

fun Result<BaseResponse<TrainInfoResponseDto>>.toModel() : Result<DomainTrainInfo> {
    return this.mapCatching { baseResponse ->
        val dto = baseResponse.data

        DomainTrainInfo(
            startAt = dto.startAt,
            arriveAt = dto.arriveAt,
            type = TrainType.valueOf(dto.type),
            trainNumber = dto.trainNumber,
            price = dto.price,
            reservationId = dto.reservationId,
        )
    }
}