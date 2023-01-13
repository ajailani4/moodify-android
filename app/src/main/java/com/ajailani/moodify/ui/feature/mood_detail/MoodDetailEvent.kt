package com.ajailani.moodify.ui.feature.mood_detail

sealed class MoodDetailEvent {
    object GetMoodDetail : MoodDetailEvent()
    object DeleteMood : MoodDetailEvent()
    data class OnMenuVisibilityChanged(val isVisible: Boolean) : MoodDetailEvent()
    data class OnDeleteMoodDialogVisChanged(val isVisible: Boolean) : MoodDetailEvent()
}
