package org.sopt.korailtalk.domain.repository

import org.sopt.korailtalk.data.dto.request.DummyRequestDto
import org.sopt.korailtalk.domain.model.DummyModel

interface DummyRepository {
    suspend fun dummy(requestDto: DummyRequestDto) : Result<DummyModel>
}