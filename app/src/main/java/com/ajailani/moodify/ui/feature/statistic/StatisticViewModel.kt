package com.ajailani.moodify.ui.feature.statistic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.use_case.statistic.GetFrequentActivitiesUseCase
import com.ajailani.moodify.domain.use_case.statistic.GetMoodPercentageUseCase
import com.ajailani.moodify.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getMoodPercentageUseCase: GetMoodPercentageUseCase,
    private val getFrequentActivitiesUseCase: GetFrequentActivitiesUseCase
) : ViewModel() {
    var moodPercentageState by mutableStateOf<UIState<MoodPercentage>>(UIState.Idle)
        private set

    var frequentActivitiesState by mutableStateOf<UIState<List<FrequentActivity>>>(UIState.Idle)
        private set

    var swipeRefreshing by mutableStateOf(false)
        private set

    init {
        getMoodPercentage()
        getFrequentActivities()
    }

    fun onEvent(event: StatisticEvent) {
        when (event) {
            StatisticEvent.GetMoodPercentage -> getMoodPercentage()

            StatisticEvent.GetFrequentActivities -> getFrequentActivities()

            is StatisticEvent.OnSwipeRefresh -> swipeRefreshing = event.isRefreshed
        }
    }

    private fun getMoodPercentage() {
        moodPercentageState = UIState.Loading

        viewModelScope.launch {
            getMoodPercentageUseCase().catch {
                moodPercentageState = UIState.Error(it.localizedMessage)
            }.collect {
                moodPercentageState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun getFrequentActivities() {
        frequentActivitiesState = UIState.Loading

        viewModelScope.launch {
            getFrequentActivitiesUseCase().catch {
                frequentActivitiesState = UIState.Error(it.localizedMessage)
            }.collect {
                frequentActivitiesState = when (it) {
                    is Resource.Success -> UIState.Success(
                        it.data?.let { frequentActivities ->
                            if (frequentActivities.isNotEmpty()) {
                                frequentActivities.slice(0..2)
                            } else {
                                frequentActivities
                            }
                        }
                    )

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }
}