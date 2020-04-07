package com.evolutio.presentation.shared

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// An Coroutine alternative forRxJava debounce operator for debouncing searchViewEntries,
// the last thing we want is to overload with unnecessary API calls
internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String) -> Unit
) : SearchView.OnQueryTextListener {
    var debouncePeriod: Long = 500

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                if (newText.isNotEmpty())
                    onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}