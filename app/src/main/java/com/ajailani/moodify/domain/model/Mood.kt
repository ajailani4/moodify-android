package com.ajailani.moodify.domain.model

data class Mood(
    val id: String,
    val mood: Int,
    val activity: Activity,
    val note: String?,
    val date: String,
    val time: String
)
