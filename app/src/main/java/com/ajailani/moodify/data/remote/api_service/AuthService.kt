package com.ajailani.moodify.data.remote.api_service

import com.ajailani.moodify.data.remote.dto.UserCredentialDto
import com.ajailani.moodify.data.remote.dto.request.LoginRequest
import com.ajailani.moodify.data.remote.dto.request.RegisterRequest
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BaseResponse<UserCredentialDto>>

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<UserCredentialDto>>
}