package com.ajailani.moodify.util

import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage

class Constants {
    object DataStore {
        const val PREFERENCES_NAME = "moodifyPreferences"
        const val ACCESS_TOKEN_KEY = "accessToken"
    }
}

val moodPercentage = MoodPercentage(
    excellent = 33.3f,
    good = 25f,
    okay = 8.3f,
    bad = 16.7f,
    terrible = 16.7f
)

val frequentActivities = listOf(
    FrequentActivity(
        activity = "Membaca",
        count = 8
    ),
    FrequentActivity(
        activity = "Bersantai",
        count = 5
    ),
    FrequentActivity(
        activity = "Bermain game",
        count = 4
    )
)