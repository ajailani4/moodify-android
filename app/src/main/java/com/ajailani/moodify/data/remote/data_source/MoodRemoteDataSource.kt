package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.MoodService
import javax.inject.Inject

class MoodRemoteDataSource @Inject constructor(
    private val moodService: MoodService
) {
    suspend fun getMoods(
        page: Int,
        size: Int,
        month: Int?,
        year: Int?
    ) = moodService.getMoods(
        page = page,
        size = size,
        month = month,
        year = year
    )
}