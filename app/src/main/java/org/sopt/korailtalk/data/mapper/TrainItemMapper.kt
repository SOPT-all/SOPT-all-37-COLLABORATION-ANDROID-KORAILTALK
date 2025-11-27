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

/**
 * 서버 응답 문자열을 TrainType Enum으로 변환
 * @receiver 서버에서 받은 열차 종류 문자열 (예: "KTX", "SRT", "KTX-C")
 * @return 매칭되는 TrainType, 없으면 KTX 반환
 */
internal fun String.toTrainType(): TrainType {
    return when (this.uppercase()) {
        "KTX", "KTX-C", "KTX-S" -> TrainType.KTX
        "SRT" -> TrainType.SRT
        "ITX-N" -> TrainType.ITX_SAEMAEUL
        "FLOWER" -> TrainType.MUGUNGHWA
        "ITX-MAEUM", "ITX-마음", "ITX-M" -> TrainType.ITX_MAEUM
        else -> TrainType.KTX  // 기본값
    }
}
//KTX :  KTX
//KTX-산천 : KTX-S
//KTX-청룡 : KTX-C
//ITX-새마을 : ITX-N
//ITX-마음 : ITX-M
//무궁화호 : FLOWER

/**
 * 서버 응답 문자열을 SeatStatusType Enum으로 변환
 * @receiver 서버에서 받은 좌석 상태 문자열 (예: "예매가능", "매진임박", "매진")
 * @return 매칭되는 SeatStatusType
 */
private fun String.toSeatStatusType(): SeatStatusType {
    return SeatStatusType.fromText(this)
}

/**
 * TrainItemResponseDto를 DomainTrainItem으로 변환
 */
fun TrainItemResponseDto.toDomain(): DomainTrainItem {
    return DomainTrainItem(
        type = type.toTrainType(),  // 확장 함수 사용
        trainNumber = trailNumber,
        departureTime = startAt,
        arrivalTime = arriveAt,
        durationMinutes = duration,
        normalSeat = SeatInfo(
            type = SeatType.NORMAL,
            status = normalSeatStatus.toSeatStatusType(),  // 확장 함수 사용
            price = normalSeatPrice
        ),
        premiumSeat = premiumSeatStatus?.let { status ->
            premiumSeatPrice?.let { price ->
                SeatInfo(
                    type = SeatType.PREMIUM,
                    status = status.toSeatStatusType(),  // 확장 함수 사용
                    price = price
                )
            }
        }
    )
}
/**
 * TrainDataResponseDto를 TrainSearchResult로 변환
 * {"status":200,"message":"열차 목록 조회 성공","data":{"origin":"Seoul","destination":"Busan","totalTrains":0,"nextCursor":null,"trainList":[]}}
 */
fun TrainDataResponseDto.toDomain(): TrainSearchResult {
    return TrainSearchResult(
        origin = origin,
        destination = destination,
        totalTrains = totalTrains,
        trains = trainList.map { it.toDomain() },
        nextCursor = nextCursor?.takeIf { it.isNotEmpty() } // null처리
    )
}

/**
 * Result<BaseResponse>를 Result<Domain>으로 변환
 */
fun Result<BaseResponse<TrainDataResponseDto>>.toModel(): Result<TrainSearchResult> {
    return this.mapCatching { baseResponse ->
        baseResponse.data.toDomain()
    }
}