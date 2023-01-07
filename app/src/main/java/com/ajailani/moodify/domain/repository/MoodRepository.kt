package com.ajailani.moodify.domain.repository

import androidx.paging.PagingData
import com.ajailani.moodify.data.Resource
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
}