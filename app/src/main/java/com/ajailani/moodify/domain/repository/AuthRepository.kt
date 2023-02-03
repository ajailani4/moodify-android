package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<Resource<UserCredential>>

    fun login(
        username: String,
        password: String
    ): Flow<Resource<UserCredential>>
}