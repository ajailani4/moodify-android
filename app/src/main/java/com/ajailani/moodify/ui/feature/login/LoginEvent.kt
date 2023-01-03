package com.ajailani.moodify.ui.feature.login

sealed class LoginEvent {
    object Login : LoginEvent()
    data class OnUsernameChanged(val username: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    object OnPasswordVisibilityChanged : LoginEvent()
}