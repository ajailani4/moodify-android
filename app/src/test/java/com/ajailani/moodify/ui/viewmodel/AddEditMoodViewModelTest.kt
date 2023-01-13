package com.ajailani.moodify.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.use_case.activity.GetActivitiesUseCase
import com.ajailani.moodify.domain.use_case.mood.AddMoodUseCase
import com.ajailani.moodify.domain.use_case.mood.EditMoodUseCase
import com.ajailani.moodify.domain.use_case.mood.GetMoodDetailUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.add_edit_mood.AddEditMoodEvent
import com.ajailani.moodify.ui.feature.add_edit_mood.AddEditMoodViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddEditMoodViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var addMoodUseCase: AddMoodUseCase

    @Mock
    private lateinit var editMoodUseCase: EditMoodUseCase

    @Mock
    private lateinit var getActivitiesUseCase: GetActivitiesUseCase

    @Mock
    private lateinit var getMoodDetailUseCase: GetMoodDetailUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var addEditMoodViewModel: AddEditMoodViewModel

    private lateinit var onEvent: (AddEditMoodEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set("moodId", "abc")
        }
        addEditMoodViewModel = AddEditMoodViewModel(
            savedStateHandle,
            addMoodUseCase,
            editMoodUseCase,
            getActivitiesUseCase,
            getMoodDetailUseCase
        )
        onEvent = addEditMoodViewModel::onEvent
    }

    @Test
    fun `Add mood should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(addMoodUseCase)(
                mood = anyInt(),
                activityName = anyString(),
                note = anyOrNull(),
                date = anyString(),
                time = anyString()
            )

            onEvent(AddEditMoodEvent.AddMood)

            val isSuccess = when (addEditMoodViewModel.addMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Add mood should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(addMoodUseCase)(
                mood = anyInt(),
                activityName = anyString(),
                note = anyOrNull(),
                date = anyString(),
                time = anyString()
            )

            onEvent(AddEditMoodEvent.AddMood)

            val isSuccess = when (addEditMoodViewModel.addMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }

    @Test
    fun `Edit mood should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(Any()))

            doReturn(resource).`when`(editMoodUseCase)(
                id = anyString(),
                mood = anyInt(),
                activityName = anyString(),
                note = anyOrNull(),
                date = anyString(),
                time = anyString()
            )

            onEvent(AddEditMoodEvent.EditMood)

            val isSuccess = when (addEditMoodViewModel.editMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Edit mood should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<Any>())

            doReturn(resource).`when`(editMoodUseCase)(
                id = anyString(),
                mood = anyInt(),
                activityName = anyString(),
                note = anyOrNull(),
                date = anyString(),
                time = anyString()
            )

            onEvent(AddEditMoodEvent.EditMood)

            val isSuccess = when (addEditMoodViewModel.editMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}