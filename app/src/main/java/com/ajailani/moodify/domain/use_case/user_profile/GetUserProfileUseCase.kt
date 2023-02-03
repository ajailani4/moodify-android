package com.ajailani.moodify.domain.use_case.user_profile

import com.ajailani.moodify.domain.repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {
    operator fun invoke() = userProfileRepository.getUserProfile()
}