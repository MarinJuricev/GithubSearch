package com.evolutio.domain.feature.search

import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.AdapterData
import com.evolutio.domain.model.search.Loading
import com.evolutio.domain.model.search.Repository
import com.evolutio.domain.shared.DispatcherProvider
import com.evolutio.domain.shared.PER_PAGE_SIZE
import com.evolutio.domain.shared.UseCaseWithParams
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrepareRepositoryData @Inject constructor(
        private val dispatcherProvider: DispatcherProvider
) : UseCaseWithParams<AdapterData, List<AdapterItem>>() {

    override suspend fun buildUseCase(params: AdapterData): List<AdapterItem> =
            withContext(dispatcherProvider.provideComputationContext()) {
                val result = mutableListOf<AdapterItem>()
                val filteredList = params.adapterItems.filterIsInstance<Repository>()
                result.addAll(filteredList)

                if (result.size >= PER_PAGE_SIZE && result.size < params.totalCount)
                    result.add(Loading())

                result.distinct()
            }
}