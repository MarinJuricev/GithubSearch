package com.evolutio.domain.model.search

data class RepositoryRequest(
    val query: String,
    val sort: String,
    val page: Int = 1
)
