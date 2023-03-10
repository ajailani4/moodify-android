package com.ajailani.moodify.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toMood
import com.ajailani.moodify.data.mapper.toMoodItem
import com.ajailani.moodify.data.remote.data_source.MoodRemoteDataSource
import com.ajailani.moodify.data.remote.data_source.PagingDataSource
import com.ajailani.moodify.data.remote.dto.request.AddEditMoodRequest
import com.ajailani.moodify.domain.repository.MoodRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override fun getPagingMoods(month: Int?, year: Int?) =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = {
                PagingDataSource { page, size ->
                    moodRemoteDataSource.getMoods(
                        page = page,
                        size = size,
                        month = month,
                        year = year
                    )
                }
            }
        ).flow.map {
            it.map { moodItemDto -> moodItemDto.toMoodItem() }
        }

    override fun getMoodDetail(id: String) =
        flow {
            val response = moodRemoteDataSource.getMoodDetail(id)

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toMood()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun addMood(
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ) =
        flow {
            val response = moodRemoteDataSource.addMood(
                AddEditMoodRequest(
                    mood = mood,
                    activityName = activityName,
                    note = note,
                    date = date,
                    time = time
                )
            )

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun editMood(
        id: String,
        mood: Int,
        activityName: String,
        note: String?,
        date: String,
        time: String
    ) =
        flow {
            val response = moodRemoteDataSource.editMood(
                id = id,
                addEditMoodRequest = AddEditMoodRequest(
                    mood = mood,
                    activityName = activityName,
                    note = note,
                    date = date,
                    time = time
                )
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun deleteMood(id: String) =
        flow {
            val response = moodRemoteDataSource.deleteMood(id)

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}