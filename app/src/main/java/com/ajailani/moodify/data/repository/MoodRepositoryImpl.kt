package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toMoodItem
import com.ajailani.moodify.data.remote.data_source.MoodRemoteDataSource
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.repository.MoodRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moodRemoteDataSource: MoodRemoteDataSource
) : MoodRepository {
    override fun getMoods(
        page: Int,
        size: Int,
        month: Int?,
        year: Int?
    ) =
        flow {
            val response = moodRemoteDataSource.getMoods(
                page = page,
                size = size,
                month = month,
                year = year
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.map { moodItemDto ->
                    moodItemDto.toMoodItem()
                }))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}