package com.evolutio.domain.model.search

import com.evolutio.domain.model.search.Repository

data class SearchResponse(
    val totalCount: Int,
    val repositories: List<Repository>
)