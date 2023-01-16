package com.ajailani.moodify.domain.use_case.statistic

import com.ajailani.moodify.domain.repository.StatisticRepository
import com.ajailani.moodify.util.StatisticType
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke(type: StatisticType) =
        when (type) {
            StatisticType.MOOD_PERCENTAGE -> {
                statisticRepository.getMoodPercentage()
            }

            StatisticType.FREQUENT_ACTIVITIES -> {
                statisticRepository.getFrequentActivities()
            }
        }
}