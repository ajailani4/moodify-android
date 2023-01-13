package com.ajailani.moodify.data.remote.dto.request

data class AddEditMoodRequest(
    val mood: Int,
    val activityName: String,
    val note: String?,
    val date: String,
    val time: String
)
