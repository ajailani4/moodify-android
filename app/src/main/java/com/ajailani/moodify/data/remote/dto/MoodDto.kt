package com.ajailani.moodify.data.remote.dto

data class MoodDto(
    val id: String,
    val mood: Int,
    val activity: ActivityDto,
    val note: String?,
    val date: String,
    val time: String
)
