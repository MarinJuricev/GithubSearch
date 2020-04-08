package com.evolutio.presentation.feature.search

sealed class SearchEvent{
    data class OnQueryTextChange(val query: String): SearchEvent()
    data class OnPagingLoadMore(val newPage: Int): SearchEvent()
}