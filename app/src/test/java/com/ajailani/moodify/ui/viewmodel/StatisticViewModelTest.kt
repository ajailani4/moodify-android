package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.FrequentActivity
import com.ajailani.moodify.domain.model.MoodPercentage
import com.ajailani.moodify.domain.use_case.statistic.GetFrequentActivitiesUseCase
import com.ajailani.moodify.domain.use_case.statistic.GetMoodPercentageUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.statistic.StatisticEvent
import com.ajailani.moodify.ui.feature.statistic.StatisticViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.frequentActivities
import com.ajailani.moodify.util.moodPercentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StatisticViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getMoodPercentageUseCase: GetMoodPercentageUseCase

    @Mock
    private lateinit var getFrequentActivitiesUseCase: GetFrequentActivitiesUseCase

    private lateinit var statisticViewModel: StatisticViewModel

    private lateinit var onEvent: (StatisticEvent) -> Unit

    @Before
    fun setUp() {
        statisticViewModel = StatisticViewModel(
            getMoodPercentageUseCase,
            getFrequentActivitiesUseCase
        )
        onEvent = statisticViewModel::onEvent
    }

    @Test
    fun `Get mood percentage should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(moodPercentage))

            doReturn(resource).`when`(getMoodPercentageUseCase)()

            onEvent(StatisticEvent.GetMoodPercentage)

            val moodPercentage =
                when (val moodPercentageState = statisticViewModel.moodPercentageState) {
                    is UIState.Success -> moodPercentageState.data

                    else -> null
                }

            assertNotNull(moodPercentage)
            assertEquals(
                "Excellent mood percentage should be '35'",
                35f,
                moodPercentage?.excellent
            )
        }
    }

    @Test
    fun `Get mood percentage should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<MoodPercentage>())

            doReturn(resource).`when`(getMoodPercentageUseCase)()

            onEvent(StatisticEvent.GetMoodPercentage)

            val isSuccess = when (statisticViewModel.moodPercentageState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Get frequent activities should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(frequentActivities))

            doReturn(resource).`when`(getFrequentActivitiesUseCase)()

            onEvent(StatisticEvent.GetFrequentActivities)

            val frequentActivities =
                when (val frequentActivitiesState = statisticViewModel.frequentActivitiesState) {
                    is UIState.Success -> frequentActivitiesState.data

                    else -> null
                }

            assertNotNull(frequentActivities)
            assertEquals(
                "Frequent activities size should be 3",
                3,
                frequentActivities?.size
            )
        }
    }

    @Test
    fun `Get frequent activities should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<List<FrequentActivity>>())

            doReturn(resource).`when`(getFrequentActivitiesUseCase)()

            onEvent(StatisticEvent.GetFrequentActivities)

            val isSuccess = when (statisticViewModel.frequentActivitiesState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}