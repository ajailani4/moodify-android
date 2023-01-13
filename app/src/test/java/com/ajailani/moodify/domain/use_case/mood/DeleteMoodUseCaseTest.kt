package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.repository.MoodRepositoryFake
import com.ajailani.moodify.util.ResourceType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteMoodUseCaseTest {
    private lateinit var moodRepositoryFake: MoodRepositoryFake
    private lateinit var deleteMoodUseCase: DeleteMoodUseCase

    @Before
    fun setUp() {
        moodRepositoryFake = MoodRepositoryFake()
        deleteMoodUseCase = DeleteMoodUseCase(moodRepositoryFake)
    }

    @Test
    fun `Delete mood should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Success)

            val isSuccess = when (deleteMoodUseCase("abc").first()) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be success",
                true,
                isSuccess
            )
        }

    @Test
    fun `Delete mood should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Error)

            val isSuccess = when (deleteMoodUseCase("abc").first()) {
                is Resource.Success -> true

                is Resource.Error -> false
            }

            assertEquals(
                "Resource should be error",
                false,
                isSuccess
            )
        }
}