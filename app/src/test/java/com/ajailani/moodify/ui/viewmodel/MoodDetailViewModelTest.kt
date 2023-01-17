package com.ajailani.moodify.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.Mood
import com.ajailani.moodify.domain.use_case.mood.DeleteMoodUseCase
import com.ajailani.moodify.domain.use_case.mood.GetMoodDetailUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.mood_detail.MoodDetailEvent
import com.ajailani.moodify.ui.feature.mood_detail.MoodDetailViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.mood
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoodDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getMoodDetailUseCase: GetMoodDetailUseCase

    @Mock
    private lateinit var deleteMoodUseCase: DeleteMoodUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var moodDetailViewModel: MoodDetailViewModel

    private lateinit var onEvent: (MoodDetailEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("moodId", "abc")
        }
        moodDetailViewModel = MoodDetailViewModel(
            savedStateHandle,
            getMoodDetailUseCase,
            deleteMoodUseCase
        )
        onEvent = moodDetailViewModel::onEvent
    }

    @Test
    fun `Get mood detail should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(mood))

            doReturn(resource).`when`(getMoodDetailUseCase)(anyString())

            onEvent(MoodDetailEvent.GetMoodDetail)

            val mood =
                when (val moodDetailState = moodDetailViewModel.moodDetailState) {
                    is UIState.Success -> moodDetailState.data

                    else -> null
                }

            assertNotNull(mood)
            assertEquals("Mood id should be 'abc'", "abc", mood?.id)
        }
    }

    @Test
    fun `Get mood detail should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Mood>())

            doReturn(resource).`when`(getMoodDetailUseCase)(anyString())

            onEvent(MoodDetailEvent.GetMoodDetail)

            val isSuccess = when (moodDetailViewModel.moodDetailState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Delete mood should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            Mockito.doReturn(resource).`when`(deleteMoodUseCase)(anyString())

            onEvent(MoodDetailEvent.DeleteMood)

            val isSuccess = when (moodDetailViewModel.deleteMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Delete mood should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            Mockito.doReturn(resource).`when`(deleteMoodUseCase)(anyString())

            onEvent(MoodDetailEvent.DeleteMood)

            val isSuccess = when (moodDetailViewModel.deleteMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}