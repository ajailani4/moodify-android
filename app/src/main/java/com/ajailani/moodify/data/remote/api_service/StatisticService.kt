package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface StatisticService {
    @GET("statistics?type=MOOD_PERCENTAGE")
    suspend fun getMoodPercentage(): Response<BaseResponse<MoodPercentageDto>>

    @GET("statistics?type=FREQUENT_ACTIVITIES")
    suspend fun getFrequentActivities(): Response<BaseResponse<List<FrequentActivityDto>>>
}