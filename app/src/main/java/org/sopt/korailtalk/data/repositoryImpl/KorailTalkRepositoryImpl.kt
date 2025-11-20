package org.sopt.korailtalk.data.repositoryImpl

import org.sopt.korailtalk.data.mapper.toDto
import org.sopt.korailtalk.data.mapper.toModel
import org.sopt.korailtalk.data.service.KorailTalkApiService
import org.sopt.korailtalk.data.util.mapBody
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import retrofit2.Response
import javax.inject.Inject

class KorailTalkRepositoryImpl @Inject constructor(
    private val korailTalkService: KorailTalkApiService
) : KorailTalkRepository {
    override suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest): Response<DomainTrainInfo> {
        return korailTalkService.getTrainInfo(domainTrainInfoRequest.toDto()).mapBody {
            it.toModel()
        }
    }
}