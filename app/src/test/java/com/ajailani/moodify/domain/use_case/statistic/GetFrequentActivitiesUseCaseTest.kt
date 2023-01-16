package com.ajailani.moodify.domain.use_case.statistic

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.repository.StatisticRepositoryFake
import com.ajailani.moodify.util.ResourceType
import com.ajailani.moodify.util.frequentActivities
import com.ajailani.moodify.util.moodPercentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFrequentActivitiesUseCaseTest {
    private lateinit var statisticRepositoryFake: StatisticRepositoryFake
    private lateinit var getFrequentActivitiesUseCase: GetFrequentActivitiesUseCase

    @Before
    fun setUp() {
        statisticRepositoryFake = StatisticRepositoryFake()
        getFrequentActivitiesUseCase = GetFrequentActivitiesUseCase(statisticRepositoryFake)
    }

    @Test
    fun `Get frequent activities should return success resource`() =
        runTest(UnconfinedTestDispatcher()) {
            statisticRepositoryFake.setResourceType(ResourceType.Success)

            val actualResource = getFrequentActivitiesUseCase().first()

            assertEquals(
                "Resource should be success",
                Resource.Success(frequentActivities),
                actualResource
            )
        }

    @Test
    fun `Get frequent activities should return error resource`() =
        runTest(UnconfinedTestDispatcher()) {
            statisticRepositoryFake.setResourceType(ResourceType.Error)

            val actualResource = getFrequentActivitiesUseCase().first()

            assertEquals(
                "Resource should be error",
                Resource.Error<List<FrequentActivity>>(),
                actualResource
            )
        }
}