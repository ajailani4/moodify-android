package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.MoodDto
import com.ajailani.moodify.domain.model.Mood

fun MoodDto.toMood() =
    Mood(
        id = id,
        mood = mood,
        activity = activity,
        note = note,
        date = date,
        time = time
    )