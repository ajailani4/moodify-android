package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.MoodItemDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoodService {
    @GET("moods")
    suspend fun getMoods(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<BaseResponse<List<MoodItemDto>>>
}