package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.StatisticRemoteDataSource
import com.ajailani.moodify.data.remote.dto.FrequentActivityDto
import com.ajailani.moodify.data.remote.dto.MoodPercentageDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.StatisticRepositoryImpl
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.repository.StatisticRepository
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
class StatisticRepositoryTest {

    @Mock
    private lateinit var statisticRemoteDataSource: StatisticRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var statisticRepository: StatisticRepository

    @Before
    fun setUp() {
        statisticRepository = StatisticRepositoryImpl(
            context,
            statisticRemoteDataSource
        )
    }

    @Test
    fun `Get mood percentage should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = moodPercentageDto
                )
            )

            doReturn(response).`when`(statisticRemoteDataSource).getMoodPercentage()

            val actualResource = statisticRepository.getMoodPercentage().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(moodPercentage),
                actualResource
            )
        }

    @Test
    fun `Get mood percentage should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<MoodPercentageDto>(
                404,
                "".toResponseBody()
            )

            doReturn(response).`when`(statisticRemoteDataSource).getMoodPercentage()

            val actualResource = statisticRepository.getMoodPercentage().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<MoodPercentage>(),
                actualResource
            )
        }

    @Test
    fun `Get frequent activities should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = frequentActivitiesDto
                )
            )

            doReturn(response).`when`(statisticRemoteDataSource).getFrequentActivities()

            val actualResource = statisticRepository.getFrequentActivities().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(frequentActivities),
                actualResource
            )
        }

    @Test
    fun `Get frequent activities should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<List<FrequentActivityDto>>(
                404,
                "".toResponseBody()
            )

            doReturn(response).`when`(statisticRemoteDataSource).getFrequentActivities()

            val actualResource = statisticRepository.getFrequentActivities().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<FrequentActivity>>(),
                actualResource
            )
        }
}