package org.sopt.korailtalk.domain.repository

import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest
import retrofit2.Response

interface KorailTalkRepository {
    suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest) : Response<DomainTrainInfo>
}