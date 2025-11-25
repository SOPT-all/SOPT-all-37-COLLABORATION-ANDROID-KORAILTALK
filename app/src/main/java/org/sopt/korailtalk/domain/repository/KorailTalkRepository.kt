package org.sopt.korailtalk.domain.repository

import org.sopt.korailtalk.domain.model.DomainHomeBasicInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfo
import org.sopt.korailtalk.domain.model.DomainTrainInfoRequest

interface KorailTalkRepository {
    suspend fun getTrainInfo(domainTrainInfoRequest: DomainTrainInfoRequest): Result<DomainTrainInfo>

    suspend fun getHomeBasicInfo(): Result<DomainHomeBasicInfo>

}
