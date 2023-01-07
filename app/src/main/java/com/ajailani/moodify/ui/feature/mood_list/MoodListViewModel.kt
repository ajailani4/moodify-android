package com.ajailani.moodify.ui.feature.mood_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ajailani.moodify.util.calMonth
import com.ajailani.moodify.util.calYear
import dagger.hilt.android.lifecycle.HiltViewModel

class MoodListViewModel : ViewModel() {
    var monthPickerDialogVis by mutableStateOf(false)
        private set

    var monthMenuExpanded by mutableStateOf(false)
        private set

    var yearMenuExpanded by mutableStateOf(false)
        private set

    var selectedMonth by mutableStateOf(calMonth)
        private set

    var selectedYear by mutableStateOf(calYear)
        private set

    fun onEvent(event: MoodListEvent) {
        when (event) {
            is MoodListEvent.OnMonthPickerDialogVisChanged -> monthPickerDialogVis = event.isVisible

            is MoodListEvent.OnMonthMenuExpanded -> monthMenuExpanded = event.isExpanded

            is MoodListEvent.OnYearMenuExpanded -> yearMenuExpanded = event.isExpanded

            is MoodListEvent.OnSelectedMonthChanged -> selectedMonth = event.month

            is MoodListEvent.OnSelectedYearChanged -> selectedYear = event.year
        }
    }
}