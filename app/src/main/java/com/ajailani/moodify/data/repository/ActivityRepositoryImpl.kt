package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toActivity
import com.ajailani.moodify.data.remote.data_source.ActivityRemoteDataSource
import com.ajailani.moodify.domain.repository.ActivityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val activityRemoteDataSource: ActivityRemoteDataSource
) : ActivityRepository {
    override fun getActivities(recommended: Boolean?) =
        flow {
            val response = activityRemoteDataSource.getActivities(recommended)

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.map { activityDto ->
                    activityDto.toActivity()
                }))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}