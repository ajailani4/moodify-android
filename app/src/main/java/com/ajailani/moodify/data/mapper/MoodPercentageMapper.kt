package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.domain.model.MoodPercentage

fun MoodPercentageDto.toMoodPercentage() =
    MoodPercentage(
        excellent = excellent,
        good = good,
        okay = okay,
        bad = bad,
        terrible = terrible
    )