package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.util.StatisticType
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    fun getMoodPercentage(): Flow<Resource<MoodPercentage>>

    fun getFrequentActivities(): Flow<Resource<List<FrequentActivity>>>
}