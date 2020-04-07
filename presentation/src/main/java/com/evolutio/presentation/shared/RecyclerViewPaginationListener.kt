package com.evolutio.presentation.shared

import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evolutio.domain.shared.INITIAL_PAGE
import com.evolutio.domain.shared.PER_PAGE_SIZE


abstract class RecyclerViewPaginationListener
/**
 * Supporting only LinearLayoutManager.
 */(@field:NonNull @param:NonNull private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var isLastPage = false
    private var isLoading = false
    private var currentPage = INITIAL_PAGE

    override fun onScrolled(
        @NonNull recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage &&
            visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
            firstVisibleItemPosition >= 0 && totalItemCount >= PER_PAGE_SIZE
        ) {
            loadMoreItems(++currentPage)
        }
    }

    fun setIsLastPage(isLast: Boolean) {
        isLastPage = isLast
    }

    fun setIsLoading(loading: Boolean) {
        isLoading = loading
    }

    fun resetCurrentPage(){
        currentPage = INITIAL_PAGE
    }

    protected abstract fun loadMoreItems(newPage: Int)

}