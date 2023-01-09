package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.repository.MoodRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.mood
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoodDetailUseCaseTest {
    private lateinit var moodRepositoryFake: MoodRepositoryFake
    private lateinit var getMoodDetailUseCase: GetMoodDetailUseCase

    @Before
    fun setUp() {
        moodRepositoryFake = MoodRepositoryFake()
        getMoodDetailUseCase = GetMoodDetailUseCase(moodRepositoryFake)
    }

    @Test
    fun `Get moods should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getMoodDetailUseCase("abc").first()

            assertEquals(
                "Resource should be success",
                Resource.Success(mood),
                actualResource
            )
        }

    @Test
    fun `Get moods should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getMoodDetailUseCase("abc").first()

            assertEquals(
                "Resource should be error",
                Resource.Error<Mood>(),
                actualResource
            )
        }
}