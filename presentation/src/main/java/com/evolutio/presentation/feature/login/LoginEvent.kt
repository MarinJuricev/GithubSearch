package com.evolutio.presentation.feature.login

sealed class LoginEvent{
    object OnLoginStart: LoginEvent()
    data class OnGetAccessToken(val code: String): LoginEvent()
}