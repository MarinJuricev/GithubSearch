package com.evolutio.domain.model.search

data class AdapterData(
    val totalCount: Int,
    val adapterItems: List<AdapterItem>,
    val paginationStatus: PaginationStatus
)