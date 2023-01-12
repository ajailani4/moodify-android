package com.ajailani.moodify.ui.feature.add_edit_mood

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.use_case.activity.GetActivitiesUseCase
import com.ajailani.moodify.domain.use_case.mood.AddMoodUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val addMoodUseCase: AddMoodUseCase,
    private val getActivitiesUseCase: GetActivitiesUseCase
) : ViewModel() {
    var addMoodState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var activitiesState by mutableStateOf<UIState<List<Activity>>>(UIState.Idle)
        private set

    var selectedMood by mutableStateOf(0)
        private set

    var selectedActivityName by mutableStateOf("")
        private set

    var note by mutableStateOf<String?>(null)
        private set

    var date by mutableStateOf(
        String.format("%02d-%02d-%02d", currentYear, currentMonth + 1, currentDay)
    )
        private set

    var time by mutableStateOf(String.format("%02d:%02d", currentHour, currentMinute))
        private set

    init {
        getActivities()
    }

    fun onEvent(event: AddEditMoodEvent) {
        when (event) {
            AddEditMoodEvent.AddMood -> addMood()

            is AddEditMoodEvent.OnMoodChanged -> selectedMood = event.mood

            is AddEditMoodEvent.OnActivityNameChanged -> selectedActivityName = event.activityName

            is AddEditMoodEvent.OnNoteChanged -> note = event.note

            is AddEditMoodEvent.OnDateChanged -> date = event.date

            is AddEditMoodEvent.OnTimeChanged -> time = event.time
        }
    }

    private fun addMood() {
        addMoodState = UIState.Loading

        viewModelScope.launch {
            addMoodUseCase(
                mood = selectedMood,
                activityName = selectedActivityName,
                note = note,
                date = date,
                time = time
            ).catch {
                addMoodState = UIState.Error(it.localizedMessage)
            }.collect {
                addMoodState = when (it) {
                    is Resource.Success -> UIState.Success(null)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun getActivities() {
        activitiesState = UIState.Loading

        viewModelScope.launch {
            getActivitiesUseCase().catch {
                activitiesState = UIState.Error(it.localizedMessage)
            }.collect {
                activitiesState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }
}