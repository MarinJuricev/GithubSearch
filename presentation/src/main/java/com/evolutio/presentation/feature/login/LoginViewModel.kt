package com.evolutio.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.login.ClearAccessToken
import com.evolutio.domain.feature.login.GetAccessToken
import com.evolutio.domain.feature.login.IsAccessTokenAvailable
import com.evolutio.domain.feature.login.StartLoginFlow
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val startLoginFlow: StartLoginFlow,
    private val getAccessToken: GetAccessToken,
    private val clearAccessToken: ClearAccessToken,
    private val isAccessTokenAvailable: IsAccessTokenAvailable
) : BaseViewModel<LoginEvent>(
) {

    private val _tokenDeletion by lazy { MutableLiveData<Unit>() }
    val tokenDeletion: LiveData<Unit> get() = _tokenDeletion

    private val _accessTokenStatus by lazy { MutableLiveData<TokenStatus>() }
    val accessTokenStatus: LiveData<TokenStatus> get() = _accessTokenStatus

    private val _tokenAvailability by lazy { MutableLiveData<TokenAvailability>() }
    val tokenAvailability: LiveData<TokenAvailability> get() = _tokenAvailability

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginStart -> initiateLoginFlow()
            is LoginEvent.OnGetAccessToken -> getAccessToken(event.code)
            is LoginEvent.OnLogout -> clearAccessToken()
            is LoginEvent.OnAccessTokenStatus -> getTokenAvailability()
        }
    }

    private fun initiateLoginFlow() = viewModelScope.launch {
        startLoginFlow.execute()
    }

    private fun getAccessToken(code: String) = viewModelScope.launch {
        when (getAccessToken.execute(code)) {
            is ResultWrapper.Value -> {
                _tokenAvailability.postValue(TokenAvailability.Available)
                _accessTokenStatus.postValue(TokenStatus.Success)
            }
            is ResultWrapper.Error -> _accessTokenStatus.postValue(TokenStatus.Failure)
        }
    }

    private fun clearAccessToken() = viewModelScope.launch {
        when (clearAccessToken.execute()) {
            is Unit -> _tokenDeletion.postValue(Unit)
        }
    }

    private fun getTokenAvailability() = viewModelScope.launch {
        when (isAccessTokenAvailable.execute()) {
            "" -> _tokenAvailability.postValue(TokenAvailability.NotAvailable)
            else -> _tokenAvailability.postValue(TokenAvailability.Available)
        }
    }


}