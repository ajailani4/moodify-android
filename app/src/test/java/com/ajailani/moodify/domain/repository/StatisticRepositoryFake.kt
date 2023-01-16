package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.frequentActivities
import com.ajailani.moodify.util.moodPercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class StatisticRepositoryFake : StatisticRepository {
    private lateinit var resourceType: ResourceType

    override fun getMoodPercentage(): Flow<Resource<MoodPercentage>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(moodPercentage))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun getFrequentActivities(): Flow<Resource<List<FrequentActivity>>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(frequentActivities))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}