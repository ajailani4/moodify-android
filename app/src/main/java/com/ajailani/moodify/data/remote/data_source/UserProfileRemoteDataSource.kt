package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.UserProfileService
import javax.inject.Inject

class UserProfileRemoteDataSource @Inject constructor(
    private val userProfileService: UserProfileService
) {
    suspend fun getUserProfile() = userProfileService.getUserProfile()
}