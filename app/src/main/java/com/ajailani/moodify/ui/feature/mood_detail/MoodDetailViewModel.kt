package com.ajailani.moodify.ui.feature.mood_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.use_case.mood.GetMoodDetailUseCase
import com.ajailani.moodify.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMoodDetailUseCase: GetMoodDetailUseCase
) : ViewModel() {
    private val moodId = savedStateHandle.get<String>("moodId")

    var moodDetailState by mutableStateOf<UIState<Mood>>(UIState.Idle)
        private set

    init {
        getMoodDetail()
    }

    fun onEvent(event: MoodDetailEvent) {
        when (event) {
            MoodDetailEvent.GetMoodDetail -> getMoodDetail()
        }
    }

    private fun getMoodDetail() {
        moodDetailState = UIState.Loading

        viewModelScope.launch {
            moodId?.let { id ->
                getMoodDetailUseCase(id).catch {
                    moodDetailState = UIState.Error(it.localizedMessage)
                }.collect {
                    moodDetailState = when (it) {
                        is Resource.Success -> UIState.Success(it.data)

                        is Resource.Error -> UIState.Fail(it.message)
                    }
                }
            }
        }
    }
}