package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.response.DummyResponseDto
import org.sopt.korailtalk.domain.model.DummyModel

fun DummyResponseDto.toModel() : DummyModel {
    // Dto -> Model로 타입 변환
    return DummyModel(
        convertedData = data.toInt()
    )
}