package com.evolutio.presentation.feature.login

sealed class TokenStatus {
    object Success : TokenStatus()
    object Failure : TokenStatus()
}