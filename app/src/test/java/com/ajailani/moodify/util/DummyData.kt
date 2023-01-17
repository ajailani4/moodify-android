package com.ajailani.moodify.util

import com.ajailani.moodify.data.remote.dto.*
import com.ajailani.moodify.domain.model.*

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

val activitiesDto = listOf(
    ActivityDto(
        id = "1",
        name = "Mendengarkan musik",
        icon = "url"
    ),
    ActivityDto(
        id = "2",
        name = "Berolahraga",
        icon = "url"
    ),
    ActivityDto(
        id = "3",
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

val moodsDto = listOf(
    MoodItemDto(
        id = "1",
        mood = 4,
        activity = "Mendengarkan musik",
        date = "2023-01-05",
        time = "10:00"
    ),
    MoodItemDto(
        id = "2",
        mood = 5,
        activity = "Berolahraga",
        date = "2023-01-05",
        time = "10:00"
    )
)

val mood = Mood(
    id = "abc",
    mood = 4,
    note = "This is a note",
    activity = Activity(
        name = "Mendengarkan musik",
        icon = "url"
    ),
    date = "2023-01-09",
    time = "11:20"
)

val moodDto = MoodDto(
    id = "abc",
    mood = 4,
    note = "This is a note",
    activity = ActivityDto(
        id = "abc",
        name = "Mendengarkan musik",
        icon = "url"
    ),
    date = "2023-01-09",
    time = "11:20"
)

val moodPercentage = MoodPercentage(
    excellent = 35f,
    good = 25f,
    okay = 15f,
    bad = 15f,
    terrible = 10f
)

val moodPercentageDto = MoodPercentageDto(
    excellent = 35f,
    good = 25f,
    okay = 15f,
    bad = 15f,
    terrible = 10f
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

val frequentActivitiesDto = listOf(
    FrequentActivityDto(
        activity = "Membaca",
        count = 8
    ),
    FrequentActivityDto(
        activity = "Bersantai",
        count = 5
    ),
    FrequentActivityDto(
        activity = "Bermain game",
        count = 4
    )
)

val userProfile = UserProfile(
    name = "George Zayvich",
    username = "george",
    email = "george@email.com"
)