package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.activities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ActivityRepositoryFake : ActivityRepository {
    private lateinit var resourceType: ResourceType

    override fun getActivities(recommended: Boolean): Flow<Resource<List<Activity>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(activities))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}