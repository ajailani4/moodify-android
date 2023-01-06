package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getActivities(recommended: Boolean): Flow<Resource<List<Activity>>>
}