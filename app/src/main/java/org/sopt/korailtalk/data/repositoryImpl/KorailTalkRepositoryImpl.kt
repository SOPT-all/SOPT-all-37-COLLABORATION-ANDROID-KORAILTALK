package org.sopt.korailtalk.data.repositoryImpl

import org.sopt.korailtalk.data.mapper.toDomain
import org.sopt.korailtalk.data.mapper.toDto
import org.sopt.korailtalk.data.mapper.toModel
import org.sopt.korailtalk.data.service.KorailTalkApiService
import org.sopt.korailtalk.data.util.safeApiCall
import org.sopt.korailtalk.domain.model.DomainHomeBasicInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.model.TrainSearchResult
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import javax.inject.Inject

class KorailTalkRepositoryImpl @Inject constructor(
    private val korailTalkService: KorailTalkApiService
) : KorailTalkRepository {
    override suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest, trainId: Long): Result<DomainTrainInfo> = safeApiCall {
        return korailTalkService.getTrainInfo(domainTrainInfoRequest.toDto(), trainId).toModel()
    override suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest): Result<DomainTrainInfo> =
        safeApiCall {
            return korailTalkService.getTrainInfo(domainTrainInfoRequest.toDto()).toModel()
        }

    override suspend fun getHomeBasicInfo(): Result<DomainHomeBasicInfo> {
        return safeApiCall {
            korailTalkService.getHomeBasicInfo()
        }.toModel()
    }


    override suspend fun getTrainList(
        origin: String,
        destination: String,
        trainType: String?,
        seatType: String?,
        isBookAvailable: Boolean?,
        cursor: String?
    ): Result<TrainSearchResult> = safeApiCall {
        return korailTalkService.getTrains(
            origin = origin,
            destination = destination,
            trainType = trainType,
            seatType = seatType,
            isBookAvailable = isBookAvailable,
            cursor = cursor
        ).toModel()
    }
}
