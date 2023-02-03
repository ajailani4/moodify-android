package com.ajailani.moodify.domain.use_case.user_credential

import com.ajailani.moodify.domain.repository.UserCredentialRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val userCredentialRepository: UserCredentialRepository
) {
    operator fun invoke() = userCredentialRepository.getAccessToken()
}