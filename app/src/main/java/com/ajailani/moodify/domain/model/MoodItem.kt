package com.ajailani.moodify.domain.model

data class MoodItem(
    val id: String,
    val mood: Int,
    val activity: String,
    val date: String,
    val time: String
)
