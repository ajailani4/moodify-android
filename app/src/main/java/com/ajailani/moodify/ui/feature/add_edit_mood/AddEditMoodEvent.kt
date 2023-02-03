package com.ajailani.moodify.ui.feature.add_edit_mood

sealed class AddEditMoodEvent {
    object Idle : AddEditMoodEvent()
    object AddMood : AddEditMoodEvent()
    object EditMood : AddEditMoodEvent()
    data class OnMoodChanged(val mood: Int) : AddEditMoodEvent()
    data class OnActivityNameChanged(val activityName: String) : AddEditMoodEvent()
    data class OnNoteChanged(val note: String?) : AddEditMoodEvent()
    data class OnDateChanged(val date: String) : AddEditMoodEvent()
    data class OnTimeChanged(val time: String) : AddEditMoodEvent()
}
