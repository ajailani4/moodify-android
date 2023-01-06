package com.ajailani.moodify.ui.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.use_case.activity.GetActivitiesUseCase
import com.ajailani.moodify.domain.use_case.mood.GetMoodsUseCase
import com.ajailani.moodify.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getActivitiesUseCase: GetActivitiesUseCase,
    private val getMoodsUseCase: GetMoodsUseCase
) : ViewModel() {
    var recommendedActivitiesState by mutableStateOf<UIState<List<Activity>>>(UIState.Idle)
        private set

    var moodsState by mutableStateOf<UIState<List<MoodItem>>>(UIState.Idle)
        private set

    var swipeRefreshing by mutableStateOf(false)
        private set

    init {
        getRecommendedActivities()
        getMoods()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetRecommendedActivities -> getRecommendedActivities()

            HomeEvent.GetMoods -> getMoods()

            is HomeEvent.OnSwipeRefresh -> swipeRefreshing = event.isRefreshed
        }
    }

    private fun getRecommendedActivities() {
        recommendedActivitiesState = UIState.Loading

        viewModelScope.launch {
            getActivitiesUseCase(true).catch {
                recommendedActivitiesState = UIState.Error(it.localizedMessage)
            }.collect {
                recommendedActivitiesState = when (it) {
                    is Resource.Success -> UIState.Success(it.data?.slice(0..2))

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun getMoods() {
        moodsState = UIState.Loading

        viewModelScope.launch {
            getMoodsUseCase(
                page = 1,
                size = 5
            ).catch {
                moodsState = UIState.Error(it.localizedMessage)
            }.collect {
                moodsState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }
}