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
class AddMoodUseCaseTest {
    private lateinit var moodRepositoryFake: MoodRepositoryFake
    private lateinit var addMoodUseCase: AddMoodUseCase

    @Before
    fun setUp() {
        moodRepositoryFake = MoodRepositoryFake()
        addMoodUseCase = AddMoodUseCase(moodRepositoryFake)
    }

    @Test
    fun `Get moods should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = addMoodUseCase(
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

            assertEquals(
                "Resource should be success",
                true,
                isSuccess
            )
        }

    @Test
    fun `Get moods should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = addMoodUseCase(
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

            assertEquals(
                "Resource should be error",
                false,
                isSuccess
            )
        }
}