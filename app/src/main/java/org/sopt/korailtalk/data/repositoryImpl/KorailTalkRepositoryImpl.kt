package org.sopt.korailtalk.data.repositoryImpl

import org.sopt.korailtalk.data.mapper.toDto
import org.sopt.korailtalk.data.mapper.toModel
import org.sopt.korailtalk.data.service.KorailTalkApiService
import org.sopt.korailtalk.data.util.safeApiCall
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import javax.inject.Inject

class KorailTalkRepositoryImpl @Inject constructor(
    private val korailTalkService: KorailTalkApiService
) : KorailTalkRepository {
    override suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest, trainId: Long): Result<DomainTrainInfo> = safeApiCall {
        return korailTalkService.getTrainInfo(domainTrainInfoRequest.toDto(), trainId).toModel()
    }
}