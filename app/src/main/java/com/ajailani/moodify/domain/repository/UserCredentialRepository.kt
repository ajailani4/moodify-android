package com.ajailani.moodify.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserCredentialRepository {
    suspend fun saveAccessToken(accessToken: String)

    fun getAccessToken(): Flow<String>

    suspend fun deleteAccessToken()
}