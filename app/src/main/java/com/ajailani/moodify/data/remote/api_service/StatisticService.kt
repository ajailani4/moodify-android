package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticService {
    @GET("statistics")
    suspend fun <T> getStatistic(
        @Query("type") type: String
    ): Response<BaseResponse<T>>
}