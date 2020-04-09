package com.evolutio.presentation.feature.login

sealed class TokenAvailability {
    object Available: TokenAvailability()
    object NotAvailable: TokenAvailability()
}