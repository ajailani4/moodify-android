package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.StatisticService
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.util.StatisticType
import javax.inject.Inject

class StatisticRemoteDataSource @Inject constructor(
    private val statisticService: StatisticService
) {
    suspend fun <T> getStatistic(type: String) =
        statisticService.getStatistic<T>(type)
}