package com.ajailani.moodify.domain.use_case.auth

import com.ajailani.moodify.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        username: String,
        password: String
    ) = authRepository.register(
        name = name,
        email = email,
        username = username,
        password = password
    )
}