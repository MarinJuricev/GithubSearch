package com.evolutio.domain.service

interface ILoginService {

    fun requestLogin()
    fun getAccessToken(): String
    fun saveAccessToken(accessToken: String)
}