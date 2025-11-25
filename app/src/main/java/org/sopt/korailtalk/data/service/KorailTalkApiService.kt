package org.sopt.korailtalk.data.service

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.request.TrainInfoRequestDto
import org.sopt.korailtalk.data.dto.response.TrainInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface KorailTalkApiService {

    @POST("/api/v1/trains/{trainId}")
    suspend fun getTrainInfo(@Body trainInfoRequestDto: TrainInfoRequestDto, @Path("trainId") trainId: Long): Result<BaseResponse<TrainInfoResponseDto>>

}