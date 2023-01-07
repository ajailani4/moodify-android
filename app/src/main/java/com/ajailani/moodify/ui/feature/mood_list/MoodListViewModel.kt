package com.ajailani.moodify.ui.feature.mood_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.use_case.mood.GetPagingMoodsUseCase
import com.ajailani.moodify.util.calMonth
import com.ajailani.moodify.util.calYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodListViewModel @Inject constructor(
    private val getPagingMoodsUseCase: GetPagingMoodsUseCase
) : ViewModel() {
    private var _pagingMoods = MutableStateFlow<PagingData<MoodItem>>(PagingData.empty())
    val pagingMoods = _pagingMoods.asStateFlow()

    var monthPickerDialogVis by mutableStateOf(false)
        private set

    var monthMenuExpanded by mutableStateOf(false)
        private set

    var yearMenuExpanded by mutableStateOf(false)
        private set

    // Month index range is 0 until 11
    var selectedMonth by mutableStateOf(calMonth)
        private set

    var selectedYear by mutableStateOf(calYear)
        private set

    var swipeRefreshing by mutableStateOf(false)
        private set

    init {
        getPagingMoods()
    }

    fun onEvent(event: MoodListEvent) {
        when (event) {
            MoodListEvent.GetPagingMoods -> getPagingMoods()

            is MoodListEvent.OnMonthPickerDialogVisChanged -> monthPickerDialogVis = event.isVisible

            is MoodListEvent.OnMonthMenuExpanded -> monthMenuExpanded = event.isExpanded

            is MoodListEvent.OnYearMenuExpanded -> yearMenuExpanded = event.isExpanded

            is MoodListEvent.OnSelectedMonthChanged -> selectedMonth = event.month

            is MoodListEvent.OnSelectedYearChanged -> selectedYear = event.year

            MoodListEvent.OnPreviousMonthClicked -> {
                if (selectedMonth == 0) {
                    if (selectedYear - 1 != 2019) {
                        selectedMonth = 11
                        selectedYear--
                    }
                } else {
                    selectedMonth--
                }
            }

            MoodListEvent.OnNextMonthClicked -> {
                if (selectedMonth == 11) {
                    if (selectedYear + 1 != 2051) {
                        selectedMonth = 0
                        selectedYear++
                    }
                } else {
                    selectedMonth++
                }
            }

            is MoodListEvent.OnSwipeRefresh -> swipeRefreshing = event.isRefreshed
        }
    }

    private fun getPagingMoods() {
        viewModelScope.launch {
            getPagingMoodsUseCase(
                month = selectedMonth + 1,
                year = selectedYear
            ).cachedIn(viewModelScope).collect {
                _pagingMoods.value = it
            }
        }
    }
}