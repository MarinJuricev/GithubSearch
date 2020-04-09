package com.evolutio.presentation.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.evolutio.domain.feature.search.GetRepositories
import com.evolutio.domain.feature.search.MapToAdapterData
import com.evolutio.domain.model.search.AdapterData
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.PaginationStatus
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
    private val mapToAdapterData: MapToAdapterData,
    private val sharedPrefsService: ISharedPrefsService
) : BaseViewModel<SearchEvent>() {

    private val _repositoryData by lazy { MutableLiveData<List<AdapterItem>>() }
    val repositoryData: LiveData<List<AdapterItem>> get() = _repositoryData

    private val _lastPage by lazy { MutableLiveData<Boolean>() }
    val lastPage: LiveData<Boolean> get() = _lastPage
    private var lastQuery = ""

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryTextChange -> getRepositoryData(event.query)
            is SearchEvent.OnPagingLoadMore -> getMoreRepositoryData(lastQuery, event.newPage)
            is SearchEvent.OnSortChanged -> resetViewModelData()
        }
    }

    private fun getRepositoryData(query: String) = viewModelScope.launch {
        loadingState.postValue(true)

        val sort = sharedPrefsService.getValue(SORT_KEY, STAR_SORT) as String
        lastQuery = query

        loadingState.postValue(true)
        val request = RepositoryRequest(
            query = query,
            sort = sort
        )
        when (val result = getRepositories.execute(request)) {
            is ResultWrapper.Value -> {
                _lastPage.postValue(result.value.repositories.size >= result.value.totalCount)
                _repositoryData.postValue(
                    mapToAdapterData.execute(
                        AdapterData(
                            totalCount = result.value.totalCount,
                            adapterItems = result.value.repositories,
                            paginationStatus = PaginationStatus.Loading
                        )
                    )
                )
            }
            is ResultWrapper.Error -> errorState.postValue(result.error.message)
        }

        loadingState.postValue(false)
    }

    private fun getMoreRepositoryData(query: String, newPage: Int) = viewModelScope.launch {
        loadingState.postValue(true)

        if (query.isBlank()) {
            errorState.postValue("Please enter a query in the Search!")
            return@launch
        }

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
                    mapToAdapterData.execute(
                        AdapterData(
                            totalCount = result.value.totalCount,
                            adapterItems = dataToBeFiltered,
                            paginationStatus = PaginationStatus.Loading
                        )
                    )
                )
            }
            is ResultWrapper.Error -> {
                _repositoryData.postValue(
                    mapToAdapterData.execute(
                        AdapterData(
                            totalCount = -1,
                            adapterItems = repositoryData.value ?: listOf(),
                            paginationStatus = PaginationStatus.Error(
                                result.error.message ?: "Unknown Error Occurred"
                            )
                        )
                    )
                )
                errorState.postValue(result.error.message)
            }
        }

        loadingState.postValue(false)
    }


    private fun resetViewModelData() {
        _repositoryData.postValue(emptyList())
        _lastPage.postValue(false)
        lastQuery = ""
    }


}
