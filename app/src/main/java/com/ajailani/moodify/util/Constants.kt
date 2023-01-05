package com.ajailani.moodify.util

import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.MoodItem

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "moodifyPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}

val activities = listOf(
    Activity(
        id = "1",
        name = "Mendengarkan musik",
        icon = "abcd"
    ),
    Activity(
        id = "2",
        name = "Olahraga",
        icon = "abcd"
    ),
    Activity(
        id = "3",
        name = "Berinteraksi dengan teman",
        icon = "abcd"
    )
)

val moods = listOf(
    MoodItem(
        id = "1",
        mood = 5,
        activity = "Mendengarkan musik",
        date = "2023-01-05",
        time = "08:00"
    ),
    MoodItem(
        id = "2",
        mood = 4,
        activity = "Olahraga",
        date = "2023-01-05",
        time = "08:00"
    ),
    MoodItem(
        id = "1",
        mood = 3,
        activity = "Berinteraksi dengan teman",
        date = "2023-01-05",
        time = "08:00"
    )
)