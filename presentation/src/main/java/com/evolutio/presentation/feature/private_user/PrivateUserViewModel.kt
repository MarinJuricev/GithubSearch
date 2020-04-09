package com.evolutio.presentation.feature.private_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.private_user.GetPrivateUserData
import com.evolutio.domain.feature.user_detail.PrepareUserData
import com.evolutio.domain.model.user.UserItem
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrivateUserViewModel @Inject constructor(
    private val getPrivateUserData: GetPrivateUserData,
    private val prepareUserData: PrepareUserData
) : BaseViewModel<PrivateUserEvent>() {

    private val _userData by lazy { MutableLiveData<List<UserItem>>() }
    val userData: LiveData<List<UserItem>> get() = _userData

    override fun handleEvent(event: PrivateUserEvent) {
        when (event) {
            is PrivateUserEvent.OnStart -> fetchUserData()
        }
    }

    private fun fetchUserData() = viewModelScope.launch {
        when (val result = getPrivateUserData.execute()) {
            is ResultWrapper.Value -> _userData.postValue(prepareUserData.execute(result.value))
            is ResultWrapper.Error -> errorState.postValue(result.error.message ?: "UNKNOWN_ERROR")
        }

        loadingState.postValue(false)
    }

}