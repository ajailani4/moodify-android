package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.StatisticService
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.util.StatisticType
import javax.inject.Inject

class StatisticRemoteDataSource @Inject constructor(
    private val statisticService: StatisticService
) {
    suspend fun getStatistic(type: StatisticType) =
        when (type) {
            StatisticType.MOOD_PERCENTAGE -> {
                statisticService.getStatistic<MoodPercentageDto>(type.toString())
            }

            StatisticType.FREQUENT_ACTIVITIES -> {
                statisticService.getStatistic<List<FrequentActivityDto>>(type.toString())
            }
        }
}