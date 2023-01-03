package com.ajailani.moodify.domain.repository

import android.net.wifi.hotspot2.pps.Credential.UserCredential
import com.ajailani.moodify.data.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<Resource<UserCredential>>
}