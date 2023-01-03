package com.ajailani.moodify.util

import com.ajailani.moodify.data.remote.dto.UserCredentialDto
import com.ajailani.moodify.domain.model.UserCredential

val userCredential = UserCredential("a1b2c3")
val userCredentialDto = UserCredentialDto(
    username = "george",
    accessToken = "a1b2c3"
)