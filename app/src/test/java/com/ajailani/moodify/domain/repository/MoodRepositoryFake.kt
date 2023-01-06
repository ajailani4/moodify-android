package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.moods
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MoodRepositoryFake : MoodRepository {
    private lateinit var resourceType: ResourceType

    override fun getMoods(
        page: Int,
        size: Int,
        month: Int?,
        year: Int?
    ): Flow<Resource<List<MoodItem>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(moods))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}