package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.util.StatisticType
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    fun getStatistic(type: StatisticType): Flow<Resource<Any>>
}