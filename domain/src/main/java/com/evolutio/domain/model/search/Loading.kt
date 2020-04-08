package com.evolutio.domain.model.search

sealed class PaginationStatus : AdapterItem {
    object Loading : PaginationStatus()
    data class Error(val errorMessage: String) : PaginationStatus()
}
