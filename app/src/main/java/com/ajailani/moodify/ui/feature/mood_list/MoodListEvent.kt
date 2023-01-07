package com.ajailani.moodify.ui.feature.mood_list

sealed class MoodListEvent {
    data class OnMonthPickerDialogVisChanged(val isVisible: Boolean) : MoodListEvent()
    data class OnMonthMenuExpanded(val isExpanded: Boolean) : MoodListEvent()
    data class OnYearMenuExpanded(val isExpanded: Boolean) : MoodListEvent()
    data class OnSelectedMonthChanged(val month: Int) : MoodListEvent()
    data class OnSelectedYearChanged(val year: Int) : MoodListEvent()
}
