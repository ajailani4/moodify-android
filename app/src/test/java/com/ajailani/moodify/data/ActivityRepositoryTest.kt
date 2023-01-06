package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.ActivityRemoteDataSource
import com.ajailani.moodify.data.remote.dto.ActivityDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.ActivityRepositoryImpl
import com.ajailani.moodify.domain.repository.ActivityRepository
import com.ajailani.moodify.util.activities
import com.ajailani.moodify.util.activitiesDto
import com.ajailani.moodify.util.userCredentialDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
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
class ActivityRepositoryTest {

    @Mock
    private lateinit var activityRemoteDataSource: ActivityRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var activityRepository: ActivityRepository

    @Before
    fun setUp() {
        activityRepository = ActivityRepositoryImpl(
            context,
            activityRemoteDataSource
        )
    }

    @Test
    fun `Get activities should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = activitiesDto
                )
            )

            doReturn(response).`when`(activityRemoteDataSource).getActivities(true)

            val actualResource = activityRepository.getActivities(true).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(activities),
                actualResource
            )
        }

    @Test
    fun `Get activities should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<List<ActivityDto>>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(activityRemoteDataSource).getActivities(true)

            val actualResource = activityRepository.getActivities(true).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<ActivityDto>>(),
                actualResource
            )
        }
}