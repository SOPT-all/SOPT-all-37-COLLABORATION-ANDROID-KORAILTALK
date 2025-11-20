package org.sopt.korailtalk.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.korailtalk.BuildConfig
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Kotlinx Serialization Json 설정
     *
     * - isLenient: JSON 형식이 엄격하지 않아도 파싱 허용
     * - prettyPrint: 출력 시 보기 좋게 포맷팅 (디버깅 용)
     * - explicitNulls: null 값을 JSON에 명시하지 않음
     * - ignoreUnknownKeys: 모델에 없는 JSON 키는 무시 (서버 스펙 변경에 유연)
     */
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesJson(): Json =
        Json {
            isLenient = true
            prettyPrint = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }


    /**
     * OkHttpClient 인스턴스
     * - timeout: 연결/쓰기/읽기 각 10초
     * - 로깅 인터셉터 추가 (API 요청/응답 로그)
     * - 인증 인터셉터 추가 (토큰 자동 주입)
     */
    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            addInterceptor { chain ->
                //TODO 나중에 custom 헤더 추가되면 여기로
                val request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
        }.build()

    /**
     * HTTP 로깅 인터셉터
     */
    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    /**
    * Retrofit 인스턴스
    * - Base URL: BuildConfig 에서 주입
    * - Converter: Kotlinx Serialization 사용
    * - HTTP Client: 설정된 OkHttpClient 사용
    */
    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull()))
            )
            .build()
}