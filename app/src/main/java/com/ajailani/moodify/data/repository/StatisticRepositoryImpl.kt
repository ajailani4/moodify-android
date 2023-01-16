package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.remote.data_source.StatisticRemoteDataSource
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
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
    override fun getStatistic(type: StatisticType): Flow<Resource<Any>> =
        flow {
            val response = when (type) {
                StatisticType.MOOD_PERCENTAGE -> {
                    statisticRemoteDataSource.getStatistic<MoodPercentage>(type.toString())
                }

                StatisticType.FREQUENT_ACTIVITIES -> {
                    statisticRemoteDataSource.getStatistic<List<FrequentActivityDto>>(type.toString())
                }
            }

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}