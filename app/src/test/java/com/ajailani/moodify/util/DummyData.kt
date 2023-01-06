package com.ajailani.moodify.util

import com.ajailani.moodify.data.remote.dto.UserCredentialDto
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.model.UserCredential

val userCredential = UserCredential("a1b2c3")

val userCredentialDto = UserCredentialDto(
    username = "george",
    accessToken = "a1b2c3"
)

val activities = listOf(
    Activity(
        name = "Mendengarkan musik",
        icon = "url"
    ),
    Activity(
        name = "Berolahraga",
        icon = "url"
    ),
    Activity(
        name = "Belajar",
        icon = "url"
    )
)

val moods = listOf(
    MoodItem(
        id = "1",
        mood = 4,
        activity = "Mendengarkan musik",
        date = "2023-01-05",
        time = "10:00"
    ),
    MoodItem(
        id = "2",
        mood = 5,
        activity = "Berolahraga",
        date = "2023-01-05",
        time = "10:00"
    )
)