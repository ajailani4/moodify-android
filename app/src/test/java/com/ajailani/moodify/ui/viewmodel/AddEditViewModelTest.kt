package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.use_case.activity.GetActivitiesUseCase
import com.ajailani.moodify.domain.use_case.mood.AddMoodUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.add_edit_mood.AddEditMoodEvent
import com.ajailani.moodify.ui.feature.add_edit_mood.AddEditViewModel
import com.ajailani.moodify.ui.feature.login.LoginEvent
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
class AddEditViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var addMoodUseCase: AddMoodUseCase

    @Mock
    private lateinit var getActivitiesUseCase: GetActivitiesUseCase

    private lateinit var addEditViewModel: AddEditViewModel

    private lateinit var onEvent: (AddEditMoodEvent) -> Unit

    @Before
    fun setUp() {
        addEditViewModel = AddEditViewModel(
            addMoodUseCase,
            getActivitiesUseCase
        )
        onEvent = addEditViewModel::onEvent
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

            val isSuccess = when (addEditViewModel.addMoodState) {
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

            val isSuccess = when (addEditViewModel.addMoodState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}