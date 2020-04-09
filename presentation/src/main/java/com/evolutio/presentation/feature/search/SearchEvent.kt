package com.evolutio.presentation.feature.search

sealed class SearchEvent{
    object OnSortChanged: SearchEvent()
    data class OnQueryTextChange(val query: String): SearchEvent()
    data class OnPagingLoadMore(val newPage: Int): SearchEvent()
}