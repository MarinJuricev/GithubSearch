package com.evolutio.data.service

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.evolutio.domain.service.IEncryptedPrefsService
import com.evolutio.domain.shared.ACCESS_TOKEN_KEY
import com.evolutio.domain.shared.GITHUB_FILE_NAME

class EncryptedPrefsServiceImpl(context: Context) : IEncryptedPrefsService {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        GITHUB_FILE_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveValue(key: String, value: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, value)
            .apply()
    }

    override fun getValue(key: String, defaultValue: String): String? =
        sharedPreferences.getString(key, defaultValue)
}