package com.ajailani.moodify.ui.viewmodel

import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserCredential
import com.ajailani.moodify.domain.use_case.auth.LoginAccountUseCase
import com.ajailani.moodify.domain.use_case.user_credential.SaveAccessTokenUseCase
import com.ajailani.moodify.ui.common.UIState
import com.ajailani.moodify.ui.feature.login.LoginEvent
import com.ajailani.moodify.ui.feature.login.LoginViewModel
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
class LoginViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var loginAccountUseCase: LoginAccountUseCase

    @Mock
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var onEvent: (LoginEvent) -> Unit

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(
            loginAccountUseCase,
            saveAccessTokenUseCase
        )
        onEvent = loginViewModel::onEvent
    }

    @Test
    fun `Login account should be success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(userCredential))

            doReturn(resource).`when`(loginAccountUseCase)(
                username = anyString(),
                password = anyString()
            )

            onEvent(LoginEvent.Login)

            val isSuccess = when (loginViewModel.loginState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be success", true, isSuccess)
        }
    }

    @Test
    fun `Login account should be fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Error<UserCredential>())

            doReturn(resource).`when`(loginAccountUseCase)(
                username = anyString(),
                password = anyString()
            )

            onEvent(LoginEvent.Login)

            val isSuccess = when (loginViewModel.loginState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}