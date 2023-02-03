package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.domain.repository.MoodRepository
import javax.inject.Inject

class GetMoodDetailUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(id: String) = moodRepository.getMoodDetail(id)
}