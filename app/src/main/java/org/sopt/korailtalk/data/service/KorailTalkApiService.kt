package org.sopt.korailtalk.data.service

import org.sopt.korailtalk.data.dto.base.BaseResponse
import org.sopt.korailtalk.data.dto.request.NationalVerifyRequestDto
import org.sopt.korailtalk.data.dto.request.TrainInfoRequestDto
import org.sopt.korailtalk.data.dto.response.HomeBasicInfoResponseDto
import org.sopt.korailtalk.data.dto.response.NationalVerifyResponseDto
import org.sopt.korailtalk.data.dto.response.TrainDataResponseDto
import org.sopt.korailtalk.data.dto.response.TrainInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface KorailTalkApiService {

    @POST("/api/v1/trains/{trainId}")
    suspend fun getTrainInfo(@Body trainInfoRequestDto: TrainInfoRequestDto, @Path("trainId") trainId: Long): BaseResponse<TrainInfoResponseDto>

    @GET("/api/v1/trains/home")
    suspend fun getHomeBasicInfo(): BaseResponse<HomeBasicInfoResponseDto>


    @GET("/api/v1/trains")
    suspend fun getTrains(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("trainType") trainType: String? = null,
        @Query("seatType") seatType: String? = null,
        @Query("isBookAvailable") isBookAvailable: Boolean? = null,
        @Query("cursor") cursor: String? = null
    ): Result<BaseResponse<TrainDataResponseDto>>  // Result 추가

    @POST("/api/v1/national/verify")
    suspend fun postVerifyNational(
        @Body nationalVerifyRequestDto: NationalVerifyRequestDto
    ): BaseResponse<NationalVerifyResponseDto>
}