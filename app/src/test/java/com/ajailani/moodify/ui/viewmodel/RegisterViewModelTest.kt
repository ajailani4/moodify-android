package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.domain.use_case.auth.RegisterAccountUseCase
import com.ajailani.moodify.domain.use_case.user_credential.SaveAccessTokenUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.register.RegisterEvent
import com.ajailani.moodify.ui.feature.register.RegisterViewModel
import com.ajailani.moodify.util.TestCoroutineRule
import com.ajailani.moodify.util.userCredential
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var registerAccountUseCase: RegisterAccountUseCase

    @Mock
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var onEvent: (RegisterEvent) -> Unit

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(
            registerAccountUseCase,
            saveAccessTokenUseCase
        )
        onEvent = registerViewModel::onEvent
    }

    @Test
    fun `Register account should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userCredential))

            doReturn(resource).`when`(registerAccountUseCase)(
                name = anyString(),
                email = anyString(),
                username = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Register account should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserCredential>())

            doReturn(resource).`when`(registerAccountUseCase)(
                name = anyString(),
                email = anyString(),
                username = anyString(),
                password = anyString()
            )

            onEvent(RegisterEvent.Register)

            val isSuccess = when (registerViewModel.registerState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}