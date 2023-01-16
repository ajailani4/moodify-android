package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toFrequentActivity
import com.ajailani.moodify.data.mapper.toMoodPercentage
import com.ajailani.moodify.data.remote.data_source.StatisticRemoteDataSource
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.repository.StatisticRepository
import com.ajailani.moodify.util.StatisticType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val statisticRemoteDataSource: StatisticRemoteDataSource
) : StatisticRepository {
    override fun getMoodPercentage() =
        flow {
            val response = statisticRemoteDataSource.getStatistic<MoodPercentageDto>(
                StatisticType.MOOD_PERCENTAGE.toString()
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toMoodPercentage()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun getFrequentActivities() =
        flow {
            val response = statisticRemoteDataSource.getStatistic<List<FrequentActivityDto>>(
                StatisticType.FREQUENT_ACTIVITIES.toString()
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.map { frequentActivityDto ->
                    frequentActivityDto.toFrequentActivity()
                }))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}