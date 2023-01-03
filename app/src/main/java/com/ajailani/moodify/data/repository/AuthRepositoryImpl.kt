package com.ajailani.moodify.data.repository

import android.content.Context
import android.net.wifi.hotspot2.pps.Credential
import com.ajailani.moodify.R
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.data.mapper.toUserCredential
import com.ajailani.moodify.data.remote.data_source.AuthRemoteDataSource
import com.ajailani.moodify.data.remote.dto.request.LoginRequest
import com.ajailani.moodify.data.remote.dto.request.RegisterRequest
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun register(
        name: String,
        email: String,
        username: String,
        password: String
    ) =
        flow {
            val response = authRemoteDataSource.register(
                RegisterRequest(
                    name = name,
                    email = email,
                    username = username,
                    password = password
                )
            )

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data?.toUserCredential()))

                409 -> emit(Resource.Error(context.getString(R.string.username_already_exists)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun login(
        username: String,
        password: String
    ) =
        flow {
            val response = authRemoteDataSource.login(
                LoginRequest(
                    username = username,
                    password = password
                )
            )

            when (response.code()) {
                200 -> emit(Resource.Success(response.body()?.data?.toUserCredential()))

                401 -> emit(Resource.Error(context.getString(R.string.incorrect_username_or_pass)))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }
}