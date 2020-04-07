package com.evolutio.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {

    // This will be the only public facing API the fragments can call on our
    // viewmodels, every other method inside the viewmodel should be private.
    abstract fun handleEvent(event: T)

    protected val errorState = MutableLiveData<String>()
    val error: LiveData<String> get() = errorState

    protected val loadingState = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = loadingState
}