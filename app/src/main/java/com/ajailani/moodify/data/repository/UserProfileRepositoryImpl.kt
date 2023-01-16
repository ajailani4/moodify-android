package com.ajailani.moodify.data.repository

import android.content.Context
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toMoodPercentage
import com.ajailani.moodify.data.mapper.toUserProfile
import com.ajailani.moodify.data.remote.data_source.UserProfileRemoteDataSource
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.domain.repository.UserProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userProfileRemoteDataSource: UserProfileRemoteDataSource
) : UserProfileRepository {
    override fun getUserProfile() =
        flow {
            val response = userProfileRemoteDataSource.getUserProfile()

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toUserProfile()))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}