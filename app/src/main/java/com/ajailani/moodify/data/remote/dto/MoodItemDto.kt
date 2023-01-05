package com.ajailani.moodify.data.remote.dto

data class MoodItemDto(
    val id: String,
    val mood: Int,
    val activity: String,
    val date: String,
    val time: String
)
