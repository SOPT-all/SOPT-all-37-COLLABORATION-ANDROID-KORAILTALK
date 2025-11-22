package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.response.DummyResponseDto
import org.sopt.korailtalk.domain.model.DummyModel

fun DummyResponseDto.toModel() : Result<DummyModel> {
    // Dto -> Model로 타입 변환
    return runCatching {
        DummyModel(
            convertedData = data.toInt()
        )
    }
}