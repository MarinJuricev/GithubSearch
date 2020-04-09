package com.evolutio.domain.model.search

data class SearchResponse(
    val totalCount: Int,
    val repositories: List<Repository>
)