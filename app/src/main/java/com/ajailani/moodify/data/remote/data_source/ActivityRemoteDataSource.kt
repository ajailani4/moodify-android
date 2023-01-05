package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.ActivityService
import javax.inject.Inject

class ActivityRemoteDataSource @Inject constructor(
    private val activityService: ActivityService
) {
    suspend fun getActivities(recommended: Boolean) = activityService.getActivities(recommended)
}