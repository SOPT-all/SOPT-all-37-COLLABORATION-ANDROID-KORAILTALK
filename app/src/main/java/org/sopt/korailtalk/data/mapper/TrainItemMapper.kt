package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.response.TrainDataResponseDto
import org.sopt.korailtalk.data.dto.response.TrainItemResponseDto
import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.model.TrainSearchResult
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

// TrainItemResponseDto를 DomainTrainItem으로 변환
fun TrainItemResponseDto.toDomain(): DomainTrainItem {
    return DomainTrainItem(
        type = TrainType.from(type),
        trainNumber = trailNumber,
        departureTime = startAt,
        arrivalTime = arriveAt,
        durationMinutes = duration,
        normalSeat = SeatInfo(
            type = SeatType.NORMAL,
            status = SeatStatusType.from(normalSeatStatus),
            price = normalSeatPrice
        ),
        premiumSeat = premiumSeatStatus?.let { status ->
            premiumSeatPrice?.let { price ->
                SeatInfo(
                    type = SeatType.PREMIUM,
                    status = SeatStatusType.from(status),
                    price = price
                )
            }
        }
    )
}

// TrainDataResponseDto를 TrainSearchResult로 변환
fun TrainDataResponseDto.toDomain(): TrainSearchResult {
    return TrainSearchResult(
        origin = origin,
        destination = destination,
        totalTrains = totalTrains,
        trains = trainList.map { it.toDomain() },
        nextCursor = nextCursor.takeIf { it.isNotEmpty() }
    )
}

// Result<BaseResponse>를 Result<Domain>으로 변환
fun Result<BaseResponse<TrainDataResponseDto>>.toDomain(): Result<TrainSearchResult> {
    return this.mapCatching { baseResponse ->
        baseResponse.data.toDomain()
    }
}