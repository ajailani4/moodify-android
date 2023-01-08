package com.ajailani.moodify.data.remote.dto

import com.squareup.moshi.Json

data class MoodDto(
    val id: String,
    val mood: Int,
    @field:Json(name = "activityName")
    val activity: String,
    val note: String,
    val date: String,
    val time: String
)
