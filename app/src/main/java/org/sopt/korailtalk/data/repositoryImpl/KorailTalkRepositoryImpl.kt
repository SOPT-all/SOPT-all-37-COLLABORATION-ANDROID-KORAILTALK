package org.sopt.korailtalk.data.repositoryImpl

import org.sopt.korailtalk.data.service.KorailTalkApiService
import org.sopt.korailtalk.domain.repository.KorailTalkRepository
import javax.inject.Inject

class KorailTalkRepositoryImpl @Inject constructor(
    private val korailTalkService: KorailTalkApiService
) : KorailTalkRepository {


}