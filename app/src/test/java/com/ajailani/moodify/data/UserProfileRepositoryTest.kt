package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.UserProfileRemoteDataSource
import com.ajailani.moodify.data.remote.dto.UserProfileDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.UserProfileRepositoryImpl
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.domain.repository.UserProfileRepository
import com.ajailani.moodify.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserProfileRepositoryTest {

    @Mock
    private lateinit var userProfileRemoteDataSource: UserProfileRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var userProfileRepository: UserProfileRepository

    @Before
    fun setUp() {
        userProfileRepository = UserProfileRepositoryImpl(
            context,
            userProfileRemoteDataSource
        )
    }

    @Test
    fun `Get user profile should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = userProfileDto
                )
            )

            doReturn(response).`when`(userProfileRemoteDataSource).getUserProfile()

            val actualResource = userProfileRepository.getUserProfile().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(userProfile),
                actualResource
            )
        }

    @Test
    fun `Get user profile should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<UserProfileDto>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(userProfileRemoteDataSource).getUserProfile()

            val actualResource = userProfileRepository.getUserProfile().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<UserProfile>(),
                actualResource
            )
        }
}