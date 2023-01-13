package com.ajailani.moodify.data

import android.content.Context
import com.ajailani.moodify.data.remote.data_source.MoodRemoteDataSource
import com.ajailani.moodify.data.remote.dto.MoodDto
import com.ajailani.moodify.data.remote.dto.MoodItemDto
import com.ajailani.moodify.data.remote.dto.response.BaseResponse
import com.ajailani.moodify.data.repository.MoodRepositoryImpl
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.repository.MoodRepository
import com.ajailani.moodify.util.mood
import com.ajailani.moodify.util.moodDto
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
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

    @Test
    fun `Get mood detail should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = moodDto
                )
            )

            doReturn(response).`when`(moodRemoteDataSource).getMoodDetail(anyString())

            val actualResource = moodRepository.getMoodDetail("abc").first()

            assertEquals(
                "Resource should be success",
                Resource.Success(mood),
                actualResource
            )
        }

    @Test
    fun `Get mood detail should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            val response = Response.error<MoodDto>(
                400,
                "".toResponseBody()
            )

            doReturn(response).`when`(moodRemoteDataSource).getMoodDetail(anyString())

            val actualResource = moodRepository.getMoodDetail("abc").first()

            assertEquals(
                "Resource should be error",
                Resource.Error<Mood>(),
                actualResource
            )
        }

    @Test
    fun `Add mood should return success resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.success(
                201,
                BaseResponse(
                    code = 201,
                    status = "Created",
                    data = null
                )
            )

            doReturn(resource).`when`(moodRemoteDataSource).addMood(any())

            val actualResource = moodRepository.addMood(
                mood = 4,
                activityName = "Mendengarkan musik",
                note = null,
                date = "2023-01-12",
                time = "09:25"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be success", true, isSuccess)
        }
    }

    @Test
    fun `Add mood should return error resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(resource).`when`(moodRemoteDataSource).addMood(any())

            val actualResource = moodRepository.addMood(
                mood = 4,
                activityName = "Mendengarkan musik",
                note = null,
                date = "2023-01-12",
                time = "09:25"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be error", false, isSuccess)
        }
    }

    @Test
    fun `Edit mood should return success resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = null
                )
            )

            doReturn(resource).`when`(moodRemoteDataSource).editMood(
                id = anyString(),
                addEditMoodRequest = any()
            )

            val actualResource = moodRepository.editMood(
                id = "abc",
                mood = 4,
                activityName = "Mendengarkan musik",
                note = null,
                date = "2023-01-12",
                time = "09:25"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be success", true, isSuccess)
        }
    }

    @Test
    fun `Edit mood should return error resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(resource).`when`(moodRemoteDataSource).editMood(
                id = anyString(),
                addEditMoodRequest = any()
            )

            val actualResource = moodRepository.editMood(
                id = "abc",
                mood = 4,
                activityName = "Mendengarkan musik",
                note = null,
                date = "2023-01-12",
                time = "09:25"
            ).first()

            val isSuccess = when (actualResource) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be error", false, isSuccess)
        }
    }

    @Test
    fun `Delete mood should return success resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.success(
                200,
                BaseResponse(
                    code = 200,
                    status = "OK",
                    data = null
                )
            )

            doReturn(resource).`when`(moodRemoteDataSource).deleteMood(anyString())

            val isSuccess = when (moodRepository.deleteMood("abc").first()) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be success", true, isSuccess)
        }
    }

    @Test
    fun `Delete mood should return error resource`() {
        runTest(UnconfinedTestDispatcher()) {
            val resource = Response.error<Any>(
                400,
                "".toResponseBody()
            )

            doReturn(resource).`when`(moodRemoteDataSource).deleteMood(anyString())

            val isSuccess = when (moodRepository.deleteMood("abc").first()) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals("Resource should be error", false, isSuccess)
        }
    }
}