package com.evolutio.presentation.feature.user_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.user_detail.GetUserData
import com.evolutio.domain.feature.user_detail.PrepareUserData
import com.evolutio.domain.model.user.UserItem
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val getUserData: GetUserData,
    private val prepareUserData: PrepareUserData
) : BaseViewModel<UserDetailEvent>() {

    private val _userData by lazy { MutableLiveData<List<UserItem>>() }
    val userData: LiveData<List<UserItem>> get() = _userData

    override fun handleEvent(event: UserDetailEvent) {
        loadingState.postValue(true)

        when (event) {
            is UserDetailEvent.OnGetUserData -> getUserData(event.userUrl)
        }
    }

    private fun getUserData(userUrl: String) = viewModelScope.launch {
        when (val result = getUserData.execute(userUrl)) {
            is ResultWrapper.Value -> _userData.postValue(prepareUserData.execute(result.value))
            is ResultWrapper.Error -> errorState.postValue(result.error.message ?: "UNKNOWN_ERROR")
        }
        loadingState.postValue(false)
    }

}