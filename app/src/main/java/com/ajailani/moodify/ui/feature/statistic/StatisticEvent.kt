package com.ajailani.moodify.ui.feature.statistic

sealed class StatisticEvent {
    object GetMoodPercentage : StatisticEvent()
    object GetFrequentActivities : StatisticEvent()
    data class OnSwipeRefresh(val isRefreshed: Boolean) : StatisticEvent()
}
