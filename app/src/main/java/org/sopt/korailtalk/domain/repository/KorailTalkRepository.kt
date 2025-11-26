package org.sopt.korailtalk.domain.repository

import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.model.TrainSearchResult

interface KorailTalkRepository {
    suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest) : Result<DomainTrainInfo>

    suspend fun getTrainList(
        origin: String,
        destination: String,
        trainType: String? = null,
        seatType: String? = null,
        isBookAvailable: Boolean? = null,
        cursor: String? = null
    ): Result<TrainSearchResult>
}