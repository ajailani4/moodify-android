package com.ajailani.moodify.data.repository

import com.ajailani.moodify.data.local.PreferencesDataStore
import com.ajailani.moodify.domain.repository.UserCredentialRepository
import javax.inject.Inject

class UserCredentialRepositoryImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : UserCredentialRepository {
    override suspend fun saveAccessToken(accessToken: String) {
        preferencesDataStore.saveAccessToken(accessToken)
    }

    override fun getAccessToken() = preferencesDataStore.getAccessToken()

    override suspend fun deleteAccessToken() {
        preferencesDataStore.deleteAccessToken()
    }
}