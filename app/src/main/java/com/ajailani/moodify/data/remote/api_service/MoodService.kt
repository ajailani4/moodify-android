package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.MoodDto
import com.ajailani.moodify.data.remote.dto.MoodItemDto
import com.ajailani.moodify.data.remote.dto.request.AddEditMoodRequest
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoodService {
    @GET("moods")
    suspend fun getMoods(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("month") month: Int?,
        @Query("year") year: Int?
    ): Response<BaseResponse<List<MoodItemDto>>>

    @GET("moods/{id}")
    suspend fun getMoodDetail(
        @Path("id") id: String
    ): Response<BaseResponse<MoodDto>>

    @POST("moods")
    suspend fun addMood(
        @Body addEditMoodRequest: AddEditMoodRequest
    ): Response<BaseResponse<Any>>
}