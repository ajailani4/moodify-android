package com.ajailani.moodify.data.remote.dto.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val username: String,
    val password: String
)
