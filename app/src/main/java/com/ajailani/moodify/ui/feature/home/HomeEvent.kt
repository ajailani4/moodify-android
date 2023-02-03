package com.ajailani.moodify.ui.feature.home

sealed class HomeEvent {
    object GetRecommendedActivities : HomeEvent()
    object GetMoods : HomeEvent()
    data class OnSwipeRefresh(val isRefreshed: Boolean) : HomeEvent()
}
