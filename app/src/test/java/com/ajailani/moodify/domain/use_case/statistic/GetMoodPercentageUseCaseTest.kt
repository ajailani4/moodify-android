package com.ajailani.moodify.domain.use_case.statistic

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.repository.StatisticRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.moodPercentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoodPercentageUseCaseTest {
    private lateinit var statisticRepositoryFake: StatisticRepositoryFake
    private lateinit var getMoodPercentageUseCase: GetMoodPercentageUseCase

    @Before
    fun setUp() {
        statisticRepositoryFake = StatisticRepositoryFake()
        getMoodPercentageUseCase = GetMoodPercentageUseCase(statisticRepositoryFake)
    }

    @Test
    fun `Get mood percentage should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            statisticRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getMoodPercentageUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(moodPercentage),
                actualResource
            )
        }

    @Test
    fun `Get mood percentage should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            statisticRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getMoodPercentageUseCase().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<MoodPercentage>(),
                actualResource
            )
        }
}