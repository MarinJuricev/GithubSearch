package com.evolutio.domain.service

interface IEncryptedPrefsService {
    fun saveValue(key: String, value: String)
    fun getValue(key: String, defaultValue: String): String?
}