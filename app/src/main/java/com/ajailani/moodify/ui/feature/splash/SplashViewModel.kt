package com.ajailani.moodify.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.moodify.domain.use_case.user_credential.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    fun getAccessToken() = getAccessTokenUseCase()
}