package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.domain.repository.MoodRepository
import javax.inject.Inject

class EditMoodUseCase @Inject constructor(
    private val moodRepository: MoodRepository
) {
    operator fun invoke(
        id: String,
        mood: Int,
        activityName: String,
        note: String? = null,
        date: String,
        time: String
    ) = moodRepository.editMood(
        id = id,
        mood = mood,
        activityName = activityName,
        note = note,
        date = date,
        time = time
    )
}