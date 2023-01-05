package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.MoodItemDto
import com.ajailani.moodify.domain.model.MoodItem

fun MoodItemDto.toMoodItem() =
    MoodItem(
        id = id,
        mood = mood,
        activity = activity,
        date = date,
        time = time
    )