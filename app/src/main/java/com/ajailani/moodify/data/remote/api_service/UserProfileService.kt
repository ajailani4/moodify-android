package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.UserProfileDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserProfileService {
    @GET("profile")
    suspend fun getUserProfile(): Response<BaseResponse<UserProfileDto>>
}