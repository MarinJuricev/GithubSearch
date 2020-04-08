package com.evolutio.presentation.feature.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.login.GetAccessToken
import com.evolutio.domain.feature.login.StartLoginFlow
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val startLoginFlow: StartLoginFlow,
    private val getAccessToken: GetAccessToken
) : BaseViewModel<LoginEvent>(
) {

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginStart -> initiateLoginFlow()
            is LoginEvent.OnGetAccessToken -> getAccessToken(event.code)
        }
    }

    private fun initiateLoginFlow() = viewModelScope.launch {
        startLoginFlow.execute()
    }

    private fun getAccessToken(code: String) = viewModelScope.launch {
        when (val result = getAccessToken.execute(code)) {
            is ResultWrapper.Value -> Log.d("ACCESS_TOKEN", "YAY")
            is ResultWrapper.Error -> Log.d("ACCESS_TOKEN", "NAY")
        }
    }

}