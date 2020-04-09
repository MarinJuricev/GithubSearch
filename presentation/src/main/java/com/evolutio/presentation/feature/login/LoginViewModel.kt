package com.evolutio.presentation.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.login.ClearAccessToken
import com.evolutio.domain.feature.login.GetAccessToken
import com.evolutio.domain.feature.login.StartLoginFlow
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val startLoginFlow: StartLoginFlow,
        private val getAccessToken: GetAccessToken,
        private val clearAccessToken: ClearAccessToken
) : BaseViewModel<LoginEvent>(
) {

    private val _tokenDeletion by lazy { MutableLiveData<Unit>() }
    val tokenDeletion: LiveData<Unit> get() = _tokenDeletion

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginStart -> initiateLoginFlow()
            is LoginEvent.OnGetAccessToken -> getAccessToken(event.code)
            is LoginEvent.OnLogout -> clearAccessToken()
        }
    }

    private fun initiateLoginFlow() = viewModelScope.launch {
        startLoginFlow.execute()
    }

    private fun getAccessToken(code: String) = viewModelScope.launch {
        when (getAccessToken.execute(code)) {
            is ResultWrapper.Value -> Log.d("ACCESS_TOKEN", "Success")
            is ResultWrapper.Error -> Log.d("ACCESS_TOKEN", "Fail")
        }
    }

    private fun clearAccessToken() = viewModelScope.launch {
        when(clearAccessToken.execute()){
            is Unit -> _tokenDeletion.postValue(Unit)
            else -> errorState.postValue("Error occurred while deleting the token")
        }
    }


}