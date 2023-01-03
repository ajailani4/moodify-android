package com.ajailani.moodify.domain.use_case.auth

import com.ajailani.moodify.domain.repository.AuthRepository
import javax.inject.Inject

class LoginAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        username: String,
        password: String
    ) = authRepository.login(
        username = username,
        password = password
    )
}