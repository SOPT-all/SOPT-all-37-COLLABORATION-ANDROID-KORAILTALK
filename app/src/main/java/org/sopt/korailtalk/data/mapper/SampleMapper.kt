package org.sopt.korailtalk.data.mapper

import org.sopt.korailtalk.data.dto.response.DummyResponseDto
import org.sopt.korailtalk.domain.model.DummyModel

fun DummyResponseDto.toModel() : DummyModel {
    return DummyModel(
        convertedData = data.toInt()
    )
}