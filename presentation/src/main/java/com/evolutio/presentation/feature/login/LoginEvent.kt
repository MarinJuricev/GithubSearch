package com.evolutio.presentation.feature.login

sealed class LoginEvent{
    object OnLoginStart: LoginEvent()
    object OnLogout: LoginEvent()
    object OnAccessTokenStatus: LoginEvent()
    data class OnGetAccessToken(val code: String): LoginEvent()
}