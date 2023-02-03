package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.domain.repository.MoodRepository
import javax.inject.Inject

class GetPagingMoodsUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(
        month: Int? = null,
        year: Int? = null
    ) = moodRepository.getPagingMoods(
        month = month,
        year = year
    )
}