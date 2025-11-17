package org.sopt.korailtalk.data.repositoryImpl

import org.sopt.korailtalk.data.dto.request.DummyRequestDto
import org.sopt.korailtalk.data.mapper.toModel
import org.sopt.korailtalk.data.service.DummyService
import org.sopt.korailtalk.domain.model.DummyModel
import org.sopt.korailtalk.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyService: DummyService
) : DummyRepository{
    override suspend fun dummy(requestDto: DummyRequestDto): Result<DummyModel> = runCatching{
        // .toModel()이라는 화장함수를 이용해서 타입변환 시켜주기
        dummyService.sampleService(requestDto).data.toModel()
    }
}