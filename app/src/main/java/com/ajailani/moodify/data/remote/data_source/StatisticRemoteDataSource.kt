package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.StatisticService
import javax.inject.Inject

class StatisticRemoteDataSource @Inject constructor(
    private val statisticService: StatisticService
) {
    suspend fun getMoodPercentage() = statisticService.getMoodPercentage()

    suspend fun getFrequentActivities() = statisticService.getFrequentActivities()
}