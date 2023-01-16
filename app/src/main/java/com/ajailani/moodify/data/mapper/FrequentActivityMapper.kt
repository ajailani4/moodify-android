package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.domain.model.FrequentActivity

fun FrequentActivityDto.toFrequentActivity() =
    FrequentActivity(
        activity = activity,
        count = count
    )