package com.ajailani.moodify.domain.use_case.mood

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.repository.MoodRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.moods
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoodsUseCaseTest {
    private lateinit var moodRepositoryFake: MoodRepositoryFake
    private lateinit var getMoodsUseCase: GetMoodsUseCase

    @Before
    fun setUp() {
        moodRepositoryFake = MoodRepositoryFake()
        getMoodsUseCase = GetMoodsUseCase(moodRepositoryFake)
    }

    @Test
    fun `Get moods should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            moodRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getMoodsUseCase(
                page = 1,
                size = 10
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
            moodRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getMoodsUseCase(
                page = 1,
                size = 10
            ).first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<MoodItem>>(),
                actualResource
            )
        }
}