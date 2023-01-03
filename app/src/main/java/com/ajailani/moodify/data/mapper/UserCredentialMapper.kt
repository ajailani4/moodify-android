package com.ajailani.moodify.data.mapper

import com.ajailani.moodify.data.remote.dto.UserCredentialDto
import com.ajailani.moodify.domain.model.UserCredential

fun UserCredentialDto.toUserCredential() =
    UserCredential(accessToken = accessToken)

