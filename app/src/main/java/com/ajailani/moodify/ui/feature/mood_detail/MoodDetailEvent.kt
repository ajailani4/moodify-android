package com.ajailani.moodify.ui.feature.mood_detail

sealed class MoodDetailEvent {
    object GetMoodDetail : MoodDetailEvent()
    data class OnMenuVisibilityChanged(val isVisible: Boolean) : MoodDetailEvent()
}
