package com.evolutio.domain.service

interface ISharedPrefsService {
    fun saveValue(key: String, value: Any)
    fun getValue(key: String, defaultValue: Any): Any?
}