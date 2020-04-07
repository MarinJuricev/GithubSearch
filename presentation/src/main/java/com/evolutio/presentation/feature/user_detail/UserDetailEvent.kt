package com.evolutio.presentation.feature.user_detail

sealed class UserDetailEvent {
    data class OnGetUserData(val userUrl: String) : UserDetailEvent()
}