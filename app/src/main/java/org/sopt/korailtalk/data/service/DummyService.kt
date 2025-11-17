package org.sopt.korailtalk.data.service

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.request.DummyRequestDto
import org.sopt.korailtalk.data.dto.response.DummyResponseDto
import retrofit2.http.Body
import retrofit2.http.GET

interface DummyService {
    @GET("dummy")
    suspend fun sampleService(@Body requestDto: DummyRequestDto) : BaseResponse<DummyResponseDto>
}