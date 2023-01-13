package com.ajailani.moodify.domain.repository

import androidx.paging.PagingData
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.model.MoodItem
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    fun getMoods(
        page: Int,
        size: Int,
        month: Int?,
        year: Int?
    ): Flow<Resource<List<MoodItem>>>

    fun getPagingMoods(
        month: Int?,
        year: Int?
    ): Flow<PagingData<MoodItem>>

    fun getMoodDetail(id: String): Flow<Resource<Mood>>

    fun addMood(
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ): Flow<Resource<Any>>

    fun editMood(
        id: String,
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ): Flow<Resource<Any>>

    fun deleteMood(id: String): Flow<Resource<Any>>
}