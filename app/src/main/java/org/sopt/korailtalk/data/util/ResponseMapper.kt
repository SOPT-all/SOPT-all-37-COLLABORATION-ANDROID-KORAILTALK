package org.sopt.korailtalk.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

fun <T, R> Response<T>.mapBody(
    mapper: (T) -> R
): Response<R> {
    return if (isSuccessful && body() != null) {
        // 성공: body만 바꿔서 새 Response 생성
        Response.success(
            code(),
            mapper(body()!!)
        )
    } else {
        // 실패: errorBody/코드는 그대로 들고감
        val errorBody = errorBody()
            ?: "Unknown error".toResponseBody("text/plain".toMediaTypeOrNull())

        Response.error(
            code(),
            errorBody
        )
    }
}