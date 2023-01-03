package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.AuthService
import com.ajailani.moodify.data.remote.dto.request.RegisterRequest
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest)
}