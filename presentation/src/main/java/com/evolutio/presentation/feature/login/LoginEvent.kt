package com.evolutio.presentation.feature.login

sealed class LoginEvent{
    object OnLoginStart: LoginEvent()
    object OnClearToken: LoginEvent()
    data class OnGetAccessToken(val code: String): LoginEvent()
}