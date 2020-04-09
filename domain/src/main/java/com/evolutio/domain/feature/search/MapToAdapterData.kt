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

/**
 * Use-case used to build AdapterItems, we use it for the following flows:
 *  1. We load a data-set, and in that data-set we fetch the first {PER_PAGE_SIZE}, but
 *     the totalCount from the api is 260, to correctly indicate loading and that we inflate
 *     the LoadingViewHolder we add PaginationStatus.Loading into the list and submit it
 *  2. We reached the end of a data-set, our current data-set is 260, and the totalCount is 260
 *     in that case we remove any items which aren't of the type {Repository} and return the list
 *  3. We got a error from the backend and it got propagated through the layers to the viewmodel,
 *     in that case we add a PaginationStatus.Error with the received error to inflate our
 *     ErrorViewHolder and to signal the user in a clear way that something is wrong
 *
 * ListAdapter takes care of the diffing and after it's done with the async calculations it calls
 * the best adapter methods ( notifyItemRemoved, notifyItemInserted etc... )
 */
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