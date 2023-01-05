package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.domain.model.MoodItem
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    fun getMoods(
        page: Int,
        size: Int,
        month: Int,
        year: Int
    ): Flow<List<MoodItem>>
}