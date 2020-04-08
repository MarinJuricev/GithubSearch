package com.evolutio.domain.feature.search

import com.evolutio.domain.model.search.AdapterData
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.PaginationStatus
import com.evolutio.domain.model.search.Repository
import com.evolutio.domain.shared.DispatcherProvider
import com.evolutio.domain.shared.PER_PAGE_SIZE
import com.evolutio.domain.shared.UseCaseWithParams
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapToAdapterData @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<AdapterData, List<AdapterItem>>() {

    override suspend fun buildUseCase(params: AdapterData): List<AdapterItem> =
        withContext(dispatcherProvider.provideComputationContext()) {
            val result = mutableListOf<AdapterItem>()
            val filteredList = params.adapterItems.filterIsInstance<Repository>()
            result.addAll(filteredList)

            if (params.paginationStatus == PaginationStatus.Loading && result.size >= PER_PAGE_SIZE && result.size < params.totalCount)
                result.add(PaginationStatus.Loading)

            if (params.paginationStatus is PaginationStatus.Error)
                result.add(PaginationStatus.Error(params.paginationStatus.errorMessage))

            result.distinct()
        }
}