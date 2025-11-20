package org.sopt.korailtalk.data.service

import org.sopt.korailtalk.data.dto.request.TrainInfoRequestDto
import org.sopt.korailtalk.data.dto.response.TrainInfoResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KorailTalkApiService {

    @POST("/api/v1/trains/{trainId}")
    suspend fun getTrainInfo(@Body trainInfoRequestDto: TrainInfoRequestDto): Response<TrainInfoResponseDto>

}