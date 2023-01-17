package com.ajailani.moodify.ui.feature.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.data.Resource
import com.ajailani.moodify.domain.model.UserProfile
import com.ajailani.moodify.domain.use_case.user_credential.DeleteAccessTokenUseCase
import com.ajailani.moodify.domain.use_case.user_profile.GetUserProfileUseCase
import com.ajailani.moodify.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val deleteAccessTokenUseCase: DeleteAccessTokenUseCase
) : ViewModel() {
    var userProfileState by mutableStateOf<UIState<UserProfile>>(UIState.Idle)
        private set

    var logoutState by mutableStateOf<UIState<Nothing>>(UIState.Idle)
        private set

    var logoutDialogVisibility by mutableStateOf(false)
        private set

    init {
        getUserProfile()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetUserProfile -> getUserProfile()

            ProfileEvent.Logout -> logout()

            is ProfileEvent.OnLogoutDialogVisChanged -> logoutDialogVisibility = event.isVisible
        }
    }

    private fun getUserProfile() {
        userProfileState = UIState.Loading

        viewModelScope.launch {
            getUserProfileUseCase().catch {
                userProfileState = UIState.Error(it.localizedMessage)
            }.collect {
                userProfileState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }

    private fun logout() {
        logoutState = UIState.Loading

        viewModelScope.launch {
            deleteAccessTokenUseCase()
            logoutState = UIState.Success(null)
        }
    }
}