package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.userProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserProfileRepositoryFake : UserProfileRepository {
    private lateinit var resourceType: ResourceType

    override fun getUserProfile(): Flow<Resource<UserProfile>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userProfile))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}