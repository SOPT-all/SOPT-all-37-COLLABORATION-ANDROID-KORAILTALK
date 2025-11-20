package org.sopt.korailtalk.data.mapper

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

fun TrainInfoResponseDto.toModel() : DomainTrainInfo {
    return DomainTrainInfo(
        startAt = this.startAt,
        arriveAt = this.arriveAt,
        type = TrainType.valueOf(this.type),
        trainNumber = this.trainNumber,
        seatType = SeatType.valueOf(this.seatType),
        price = this.price,
        reservationId = this.reservationId,
    )
}