package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.domain.repository.MoodRepository
import javax.inject.Inject

class GetMoodsUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(
        page: Int,
        size: Int
    ) = moodRepository.getMoods(
        page = page,
        size = size
    )
}