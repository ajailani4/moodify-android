package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.ActivityDto
import com.ajailani.moodify.domain.model.Activity

fun ActivityDto.toActivity() =
    Activity(
        name = name,
        icon = icon
    )