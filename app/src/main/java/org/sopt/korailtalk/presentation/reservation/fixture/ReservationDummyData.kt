package org.sopt.korailtalk.presentation.reservation.fixture

import org.sopt.korailtalk.domain.model.DomainTrainItem
import org.sopt.korailtalk.domain.model.SeatInfo
import org.sopt.korailtalk.domain.model.TrainSearchResult
import org.sopt.korailtalk.domain.type.SeatStatusType
import org.sopt.korailtalk.domain.type.SeatType
import org.sopt.korailtalk.domain.type.TrainType

/**
 * 예약 화면에서 사용하는 더미 데이터
 */
object ReservationDummyData {

    /**
     * 서울 → 부산 열차 목록 더미 데이터
     */
    val seoulToBusanTrains = listOf(
        // KTX - 예약 가능
        DomainTrainItem(
            trainId = 1,
            type = TrainType.KTX,
            trainNumber = "001",
            departureTime = "05:13",
            arrivalTime = "07:50",
            durationMinutes = 157,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // KTX - 일반실 매진임박, 특실 예약가능
        DomainTrainItem(
            trainId = 2,
            type = TrainType.KTX,
            trainNumber = "003",
            departureTime = "05:30",
            arrivalTime = "08:05",
            durationMinutes = 155,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.ALMOST_SOLD_OUT,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // SRT - 예약 가능
        DomainTrainItem(
            trainId = 3,
            type = TrainType.SRT,
            trainNumber = "101",
            departureTime = "06:00",
            arrivalTime = "08:38",
            durationMinutes = 158,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // KTX - 매진
        DomainTrainItem(
            trainId = 4,
            type = TrainType.KTX,
            trainNumber = "005",
            departureTime = "06:10",
            arrivalTime = "08:47",
            durationMinutes = 157,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.SOLD_OUT,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.SOLD_OUT,
                price = 83900
            )
        ),
        // SRT - 특실 매진임박
        DomainTrainItem(
            trainId = 5,
            type = TrainType.SRT,
            trainNumber = "103",
            departureTime = "06:30",
            arrivalTime = "09:08",
            durationMinutes = 158,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.ALMOST_SOLD_OUT,
                price = 83900
            )
        ),
        // ITX-새마을 - 예약 가능
        DomainTrainItem(
            trainId = 6,
            type = TrainType.ITX_SAEMAEUL,
            trainNumber = "201",
            departureTime = "07:05",
            arrivalTime = "11:20",
            durationMinutes = 255,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 39900
            ),
            premiumSeat = null
        ),
        // KTX - 일반실 예약가능, 특실 매진
        DomainTrainItem(
            trainId = 7,
            type = TrainType.KTX,
            trainNumber = "007",
            departureTime = "07:30",
            arrivalTime = "10:05",
            durationMinutes = 155,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.SOLD_OUT,
                price = 83900
            )
        ),
        // 무궁화호 - 예약 가능 (특실 없음)
        DomainTrainItem(
            trainId = 8,
            type = TrainType.MUGUNGHWA,
            trainNumber = "1201",
            departureTime = "08:00",
            arrivalTime = "13:25",
            durationMinutes = 325,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 28800
            ),
            premiumSeat = null
        ),
        // SRT - 예약 가능
        DomainTrainItem(
            trainId = 9,
            type = TrainType.SRT,
            trainNumber = "105",
            departureTime = "08:20",
            arrivalTime = "10:58",
            durationMinutes = 158,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // ITX-마음 - 매진임박
        DomainTrainItem(
            trainId = 10,
            type = TrainType.ITX_MAEUM,
            trainNumber = "301",
            departureTime = "09:15",
            arrivalTime = "13:40",
            durationMinutes = 265,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.ALMOST_SOLD_OUT,
                price = 35200
            ),
            premiumSeat = null
        ),
        // KTX - 일반실 매진, 특실 예약가능
        DomainTrainItem(
            trainId = 11,
            type = TrainType.KTX,
            trainNumber = "009",
            departureTime = "09:40",
            arrivalTime = "12:15",
            durationMinutes = 155,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.SOLD_OUT,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // 무궁화호 - 매진 (특실 없음)
        DomainTrainItem(
            trainId = 12,
            type = TrainType.MUGUNGHWA,
            trainNumber = "1203",
            departureTime = "10:30",
            arrivalTime = "15:55",
            durationMinutes = 325,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.SOLD_OUT,
                price = 28800
            ),
            premiumSeat = null
        ),
        // SRT - 모두 예약 가능
        DomainTrainItem(
            trainId = 13,
            type = TrainType.SRT,
            trainNumber = "107",
            departureTime = "11:00",
            arrivalTime = "13:38",
            durationMinutes = 158,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // KTX - 모두 예약 가능
        DomainTrainItem(
            trainId = 14,
            type = TrainType.KTX,
            trainNumber = "011",
            departureTime = "11:30",
            arrivalTime = "14:05",
            durationMinutes = 155,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 59800
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 83900
            )
        ),
        // ITX-새마을 - 매진임박
        DomainTrainItem(
            trainId = 15,
            type = TrainType.ITX_SAEMAEUL,
            trainNumber = "203",
            departureTime = "12:15",
            arrivalTime = "16:30",
            durationMinutes = 255,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.ALMOST_SOLD_OUT,
                price = 39900
            ),
            premiumSeat = null
        )
    )

    /**
     * 서울 → 대전 열차 목록 더미 데이터
     */
    val seoulToDaejeonTrains = listOf(
        DomainTrainItem(
            trainId = 101,
            type = TrainType.KTX,
            trainNumber = "401",
            departureTime = "06:00",
            arrivalTime = "06:50",
            durationMinutes = 50,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 23700
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.AVAILABLE,
                price = 33200
            )
        ),
        DomainTrainItem(
            trainId = 102,
            type = TrainType.SRT,
            trainNumber = "501",
            departureTime = "06:30",
            arrivalTime = "07:22",
            durationMinutes = 52,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 23700
            ),
            premiumSeat = SeatInfo(
                type = SeatType.PREMIUM,
                status = SeatStatusType.ALMOST_SOLD_OUT,
                price = 33200
            )
        ),
        DomainTrainItem(
            trainId = 103,
            type = TrainType.MUGUNGHWA,
            trainNumber = "1401",
            departureTime = "07:15",
            arrivalTime = "09:05",
            durationMinutes = 110,
            normalSeat = SeatInfo(
                type = SeatType.NORMAL,
                status = SeatStatusType.AVAILABLE,
                price = 11400
            ),
            premiumSeat = null
        )
    )

    /**
     * TrainSearchResult 더미 데이터 (서울 → 부산)
     */
    val seoulToBusanSearchResult = TrainSearchResult(
        origin = "서울",
        destination = "부산",
        totalTrains = seoulToBusanTrains.size,
        trains = seoulToBusanTrains,
        nextCursor = null
    )

    /**
     * TrainSearchResult 더미 데이터 (서울 → 대전)
     */
    val seoulToDaejeonSearchResult = TrainSearchResult(
        origin = "서울",
        destination = "대전",
        totalTrains = seoulToDaejeonTrains.size,
        trains = seoulToDaejeonTrains,
        nextCursor = null
    )

    /**
     * 빈 검색 결과
     */
    val emptySearchResult = TrainSearchResult(
        origin = "서울",
        destination = "강릉",
        totalTrains = 0,
        trains = emptyList(),
        nextCursor = null
    )
}