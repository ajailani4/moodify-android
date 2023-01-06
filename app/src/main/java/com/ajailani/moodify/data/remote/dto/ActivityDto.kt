package com.ajailani.moodify.data.remote.dto

import com.squareup.moshi.Json

data class ActivityDto(
    val id: String?,
    @field:Json(name = "activityName")
    val name: String,
    val icon: String?
)
