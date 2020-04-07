package com.evolutio.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.search.GetRepositories
import com.evolutio.domain.feature.search.PrepareRepositoryData
import com.evolutio.domain.model.search.AdapterData
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.RepositoryRequest
import com.evolutio.domain.service.ISharedPrefsService
import com.evolutio.domain.shared.ResultWrapper
import com.evolutio.domain.shared.SORT_KEY
import com.evolutio.domain.shared.STAR_SORT
import com.evolutio.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getRepositories: GetRepositories,
    private val prepareRepositoryData: PrepareRepositoryData,
    private val sharedPrefsService: ISharedPrefsService
) : BaseViewModel<SearchEvent>() {

    private val _repositoryData by lazy { MutableLiveData<List<AdapterItem>>() }
    val repositoryData: LiveData<List<AdapterItem>> get() = _repositoryData

    private val _lastPage by lazy { MutableLiveData<Boolean>() }
    val lastPage: LiveData<Boolean> get() = _lastPage

    override fun handleEvent(event: SearchEvent) {
        loadingState.postValue(true)

        when (event) {
            is SearchEvent.OnQueryTextChange -> {
                getRepositoryData(event.query)
            }
            is SearchEvent.OnPagingLoadMore -> getMoreRepositoryData(event.query, event.newPage)
        }
    }

    private fun getRepositoryData(query: String) = viewModelScope.launch {
        val sort = sharedPrefsService.getValue(SORT_KEY, STAR_SORT) as String

        val request = RepositoryRequest(
            query = query,
            sort = sort
        )
        when (val result = getRepositories.execute(request)) {
            is ResultWrapper.Value -> {
                _lastPage.postValue(result.value.repositories.size >= result.value.totalCount)
                _repositoryData.postValue(
                    prepareRepositoryData.execute(
                        AdapterData(
                            totalCount = result.value.totalCount,
                            adapterItems = result.value.repositories
                        )
                    )
                )
            }
            is ResultWrapper.Error -> errorState.postValue(result.error.message)
        }

        loadingState.postValue(false)
    }

    private fun getMoreRepositoryData(query: String, newPage: Int) = viewModelScope.launch {
        val sort = sharedPrefsService.getValue(SORT_KEY, STAR_SORT) as String

        val request = RepositoryRequest(
            query = query,
            sort = sort,
            page = newPage
        )

        when (val result = getRepositories.execute(request)) {
            is ResultWrapper.Value -> {
                val dataToBeFiltered = mutableListOf<AdapterItem>()
                val previousData = repositoryData.value ?: listOf()
                val newData = result.value.repositories

                dataToBeFiltered.addAll(previousData)
                dataToBeFiltered.addAll(newData)

                _lastPage.postValue(dataToBeFiltered.size >= result.value.totalCount)
                _repositoryData.postValue(
                    prepareRepositoryData.execute(
                        AdapterData(
                            totalCount = result.value.totalCount,
                            adapterItems = dataToBeFiltered
                        )
                    )
                )
            }
            is ResultWrapper.Error -> errorState.postValue(result.error.message)
        }

        loadingState.postValue(false)
    }

}