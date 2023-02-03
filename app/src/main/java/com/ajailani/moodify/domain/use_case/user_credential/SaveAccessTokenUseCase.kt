package com.ajailani.moodify.domain.use_case.user_credential

import com.ajailani.moodify.domain.repository.UserCredentialRepository
import javax.inject.Inject

class SaveAccessTokenUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    suspend operator fun invoke(accessToken: String) =
        userCredentialRepository.saveAccessToken(accessToken)
}