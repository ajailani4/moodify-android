package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Activity
import com.ajailani.moodify.domain.model.MoodItem
import com.ajailani.moodify.domain.use_case.activity.GetActivitiesUseCase
import com.ajailani.moodify.domain.use_case.mood.GetMoodsUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.home.HomeEvent
import com.ajailani.moodify.ui.feature.home.HomeViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.activities
import com.ajailani.moodify.util.moods
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getActivitiesUseCase: GetActivitiesUseCase

    @Mock
    private lateinit var getMoodsUseCase: GetMoodsUseCase

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var onEvent: (HomeEvent) -> Unit

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getActivitiesUseCase,
            getMoodsUseCase
        )
        onEvent = homeViewModel::onEvent
    }

    @Test
    fun `Get recommended activities should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(activities))

            doReturn(resource).`when`(getActivitiesUseCase)(anyBoolean())

            onEvent(HomeEvent.GetRecommendedActivities)

            val recommendedActivities =
                when (val recommendedActivitiesState = homeViewModel.recommendedActivitiesState) {
                    is UIState.Success -> recommendedActivitiesState.data

                    else -> null
                }

            assertNotNull(recommendedActivities)
            assertEquals("Recommended activities size should be 3", 3, recommendedActivities?.size)
        }
    }

    @Test
    fun `Get recommended activities should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<List<Activity>>())

            doReturn(resource).`when`(getActivitiesUseCase)(anyBoolean())

            onEvent(HomeEvent.GetRecommendedActivities)

            val isSuccess = when (homeViewModel.recommendedActivitiesState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Get moods should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(moods))

            doReturn(resource).`when`(getMoodsUseCase)(
                page = anyInt(),
                size = anyInt(),
                month = anyOrNull(),
                year = anyOrNull()
            )

            onEvent(HomeEvent.GetMoods)

            val moods = when (val moodsState = homeViewModel.moodsState) {
                is UIState.Success -> moodsState.data

                else -> null
            }

            assertNotNull(moods)
            assertEquals("Moods size should be 2", 2, moods?.size)
        }
    }

    @Test
    fun `Get moods should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<List<MoodItem>>())

            doReturn(resource).`when`(getMoodsUseCase)(
                page = anyInt(),
                size = anyInt(),
                month = anyOrNull(),
                year = anyOrNull()
            )

            onEvent(HomeEvent.GetMoods)

            val isSuccess = when (homeViewModel.moodsState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}