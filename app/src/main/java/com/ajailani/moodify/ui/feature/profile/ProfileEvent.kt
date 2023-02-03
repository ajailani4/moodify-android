package com.ajailani.moodify.ui.feature.profile

sealed class ProfileEvent {
    object GetUserProfile : ProfileEvent()
    object Logout : ProfileEvent()
    data class OnLogoutDialogVisChanged(val isVisible: Boolean) : ProfileEvent()
}
