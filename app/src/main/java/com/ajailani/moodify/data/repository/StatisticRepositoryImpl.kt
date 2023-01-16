package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toFrequentActivity
import com.ajailani.moodify.data.mapper.toMoodPercentage
import com.ajailani.moodify.data.remote.data_source.StatisticRemoteDataSource
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.domain.repository.StatisticRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val statisticRemoteDataSource: StatisticRemoteDataSource
) : StatisticRepository {
    override fun getMoodPercentage() =
        flow {
            val response = statisticRemoteDataSource.getMoodPercentage()

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toMoodPercentage()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun getFrequentActivities() =
        flow {
            val response = statisticRemoteDataSource.getFrequentActivities()

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.map { frequentActivityDto ->
                    frequentActivityDto.toFrequentActivity()
                }))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}