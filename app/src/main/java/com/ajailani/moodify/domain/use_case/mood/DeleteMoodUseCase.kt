package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.domain.repository.MoodRepository
import javax.inject.Inject

class DeleteMoodUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(id: String) = moodRepository.deleteMood(id)
}