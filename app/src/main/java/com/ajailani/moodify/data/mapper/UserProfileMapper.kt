package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.UserProfileDto
import com.ajailani.moodify.domain.model.UserProfile

fun UserProfileDto.toUserProfile() =
    UserProfile(
        name = name,
        username = username,
        email = email
    )
