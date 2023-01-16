package com.ajailani.moodify.domain.repository

import com.ajailani.moodify.util.StatisticType

interface StatisticRepository {
    fun getStatistic(type: StatisticType)
}