package com.ajailani.moodify.domain.use_case.statistic

import com.ajailani.moodify.domain.repository.StatisticRepository
import com.ajailani.moodify.util.StatisticType
import javax.inject.Inject

class GetMoodPercentageUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke() = statisticRepository.getMoodPercentage()
}