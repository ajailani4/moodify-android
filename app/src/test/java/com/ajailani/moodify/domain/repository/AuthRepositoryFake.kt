package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.userCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryFake : AuthRepository {
    private lateinit var resourceType: ResourceType

    override fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ): Flow<Resource<UserCredential>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(userCredential))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}