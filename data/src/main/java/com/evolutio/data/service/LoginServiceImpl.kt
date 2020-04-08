package com.evolutio.data.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.evolutio.data.remote.FULL_AUTHORIZE_ENDPOINT
import com.evolutio.domain.service.IEncryptedPrefsService
import com.evolutio.domain.service.ILoginService
import com.evolutio.domain.shared.ACCESS_TOKEN_KEY

class LoginServiceImpl(
    private val context: Context?,
    private val encryptedPrefsService: IEncryptedPrefsService
) : ILoginService {
    override fun requestLogin() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(FULL_AUTHORIZE_ENDPOINT)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context?.startActivity(intent)
    }

    override fun getAccessToken(): String {
        val accessToken = encryptedPrefsService.getValue(ACCESS_TOKEN_KEY, "")
        return accessToken ?: ""
    }

    override fun saveAccessToken(accessToken: String) {
        encryptedPrefsService.saveValue(ACCESS_TOKEN_KEY, accessToken)
    }
}