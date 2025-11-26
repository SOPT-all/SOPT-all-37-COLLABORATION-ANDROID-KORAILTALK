package org.sopt.korailtalk.domain.repository

import org.sopt.korailtalk.domain.model.DomainHomeBasicInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.model.TrainSearchResult

interface KorailTalkRepository {
    suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest, trainId: Long) : Result<DomainTrainInfo>

    suspend fun getHomeBasicInfo(): Result<DomainHomeBasicInfo>

    suspend fun getTrainList(
        origin: String,
        destination: String,
        trainType: String? = null,
        seatType: String? = null,
        isBookAvailable: Boolean? = null,
        cursor: String? = null
    ): Result<TrainSearchResult>
}
