package com.ajailani.moodify.data.remote.data_source

import com.ajailani.moodify.data.remote.api_service.MoodService
import com.ajailani.moodify.data.remote.dto.request.AddEditMoodRequest
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

    suspend fun getMoodDetail(id: String) = moodService.getMoodDetail(id)

    suspend fun addMood(addEditMoodRequest: AddEditMoodRequest) =
        moodService.addMood(addEditMoodRequest)

    suspend fun editMood(
        id: String,
        addEditMoodRequest: AddEditMoodRequest
    ) = moodService.editMood(
        id = id,
        addEditMoodRequest = addEditMoodRequest
    )
}