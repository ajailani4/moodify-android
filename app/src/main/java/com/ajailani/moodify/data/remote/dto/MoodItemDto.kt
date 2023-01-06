package com.ajailani.moodify.data.remote.dto

import com.squareup.moshi.Json

data class MoodItemDto(
    val id: String,
    val mood: Int,
    @field:Json(name = "activityName")
    val activity: String,
    val date: String,
    val time: String
)
