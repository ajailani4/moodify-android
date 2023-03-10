package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.ActivityDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityService {
    @GET("activities")
    suspend fun getActivities(
        @Query("recommended") recommended: Boolean?
    ): Response<BaseResponse<List<ActivityDto>>>
}