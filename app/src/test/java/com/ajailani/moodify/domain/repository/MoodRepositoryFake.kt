package com.ajailani.moodify.domain.repository

import androidx.paging.PagingData
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.mood
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

    override fun getPagingMoods(month: Int?, year: Int?): Flow<PagingData<MoodItem>> {
        TODO("Not yet implemented")
    }

    override fun getMoodDetail(id: String): Flow<Resource<Mood>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(mood))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun addMood(
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ): Flow<Resource<Any>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(Any()))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    override fun editMood(
        id: String,
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ): Flow<Resource<Any>> =
        when (resourceType) {
            ResourceType.Success -> flowOf(Resource.Success(Any()))

            ResourceType.Error -> flowOf(Resource.Error(null))
        }

    fun setResourceType(type: ResourceType) {
        resourceType = type
    }
}