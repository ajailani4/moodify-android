package com.ajailani.moodify.domain.use_case.statistic

import com.ajailani.moodify.domain.repository.StatisticRepository
import javax.inject.Inject

class GetFrequentActivitiesUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository
) {
    operator fun invoke() = statisticRepository.getFrequentActivities()
}