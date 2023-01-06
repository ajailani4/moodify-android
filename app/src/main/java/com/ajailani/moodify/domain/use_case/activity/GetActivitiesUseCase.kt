package com.ajailani.moodify.domain.use_case.activity

import com.ajailani.moodify.domain.repository.ActivityRepository
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(recommended: Boolean) = activityRepository.getActivities(recommended)
}