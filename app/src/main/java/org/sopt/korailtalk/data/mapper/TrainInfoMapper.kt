package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.request.TrainInfoRequestDto
import org.sopt.korailtalk.data.dto.response.TrainInfoResponseDto
import org.sopt.korailtalk.domain.model.DomainCouponData
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
            startAt = dto.trainInfo.startAt,
            arriveAt = dto.trainInfo.arriveAt,
            type = TrainType.valueOf(dto.trainInfo.type),
            trainNumber = dto.trainInfo.trainNumber,
            price = dto.trainInfo.price,
            seatType = SeatType.valueOf(dto.trainInfo.seatType),
            reservationId = dto.trainInfo.reservationId,
            coupons = dto.coupons.map {
                DomainCouponData(
                    name = it.name,
                    discountRate = it.discountRate
                )
            },
        )
    }
}