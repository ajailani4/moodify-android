package com.ajailani.moodify.data.remote.dto.response

data class BaseResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)
