package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.MoodRemoteDataSource
import com.ajailani.moodify.data.remote.dto.ActivityDto
import com.ajailani.moodify.data.remote.dto.MoodItemDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.ActivityRepositoryImpl
import com.ajailani.moodify.data.repository.MoodRepositoryImpl
import com.ajailani.moodify.domain.repository.MoodRepository
import com.ajailani.moodify.util.activities
import com.ajailani.moodify.util.activitiesDto
import com.ajailani.moodify.util.moods
import com.ajailani.moodify.util.moodsDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoodRepositoryTest {

    @Mock
    private lateinit var moodRemoteDataSource: MoodRemoteDataSource

    @Mock
    private lateinit var context: Context

    private lateinit var moodRepository: MoodRepository

    @Before
    fun setUp() {
        moodRepository = MoodRepositoryImpl(
            context,
            moodRemoteDataSource
        )
    }

    @Test
    fun `Get moods should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = moodsDto
                )
            )

            doReturn(response).`when`(moodRemoteDataSource).getMoods(
                page = anyInt(),
                size = anyInt(),
                month = anyOrNull(),
                year = anyOrNull()
            )

            val actualResource = moodRepository.getMoods(
                page = 1,
                size = 10,
                month = null,
                year = null
            ).first()

            assertEquals(
                "Resource should be success",
                Resource.Success(moods),
                actualResource
            )
        }

    @Test
    fun `Get moods should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<List<MoodItemDto>>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(moodRemoteDataSource).getMoods(
                page = anyInt(),
                size = anyInt(),
                month = anyOrNull(),
                year = anyOrNull()
            )

            val actualResource = moodRepository.getMoods(
                page = 1,
                size = 10,
                month = null,
                year = null
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<MoodItemDto>>(),
                actualResource
            )
        }
}