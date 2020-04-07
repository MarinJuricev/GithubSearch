package com.evolutio.presentation.feature.search

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evolutio.domain.model.search.Repository
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.R
import com.evolutio.presentation.databinding.FragmentSearchBinding
import com.evolutio.presentation.ext.generateDividerDecoration
import com.evolutio.presentation.shared.DebouncingQueryTextListener
import com.evolutio.presentation.shared.RecyclerViewPaginationListener
import com.evolutio.presentation.shared.views.SortDialogFragment
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var searchAdapter: SearchAdapter
    private var searchMenu: Menu? = null
    private var scrollListener: RecyclerViewPaginationListener? = null

    // Does this leek ?
    // Reference https://developer.android.com/topic/libraries/view-binding
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchRecycleView()
        setupRefreshListener()

        observeRepositoryData()
        observeErrorState()

        observeLoadingState()
        observeLastPageSate()

        binding.btnSelectSort.setOnClickListener {
            val dialog = SortDialogFragment()
            dialog.show(childFragmentManager, "DALOG")
        }
    }

    private fun setupSearchRecycleView() {
        searchAdapter = SearchAdapter(
            ::openRepositoryDetails,
            ::openUserDetails
        )
        val layoutManager = LinearLayoutManager(context)

        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            setLayoutManager(layoutManager)
            addItemDecoration(context.generateDividerDecoration())
        }

        scrollListener = object : RecyclerViewPaginationListener(layoutManager) {
            override fun loadMoreItems(newPage: Int) {
                val menuItem = searchMenu?.findItem(R.id.search)
                val searchView: SearchView =
                    menuItem?.actionView as? SearchView ?: return

                dispatchMoreData(searchView.query.toString(), newPage)
            }
        }
        binding.searchRecyclerView.addOnScrollListener(scrollListener as RecyclerViewPaginationListener)
    }

    private fun setupRefreshListener() {
        binding.searchPullToRefresh.setOnRefreshListener {
            val menuItem = searchMenu?.findItem(R.id.search)
            val searchView: SearchView =
                menuItem?.actionView as? SearchView ?: return@setOnRefreshListener

            dispatchQueryMessage(searchView.query.toString())
        }
    }

    private fun openRepositoryDetails(repository: Repository) =
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToRepositoryDetailFragment(repository)
        )

    private fun openUserDetails(
        repository: Repository,
        sharedView: View
    ) {
        val extras = FragmentNavigator.Extras.Builder()
        extras.addSharedElement(sharedView, sharedView.transitionName)
        val build = extras.build()

        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToUserDetailFragment(repository),
            build
        )
    }

    private fun observeLoadingState() {
        searchViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.searchPullToRefresh.isRefreshing = isLoading
            scrollListener?.setIsLoading(isLoading)
        })
    }

    private fun observeErrorState() {
        searchViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        })
    }

    private fun observeRepositoryData() {
        searchViewModel.repositoryData.observe(viewLifecycleOwner, Observer { repositoryData ->
            searchAdapter.submitList(repositoryData)
        })
    }

    private fun observeLastPageSate() {
        searchViewModel.lastPage.observe(viewLifecycleOwner, Observer {
            scrollListener?.setIsLastPage(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_options_menu, menu)

        // Get the menu reference so we can access the current text in searchView
        // for setupRefreshListener
        searchMenu = menu

        val item = menu.findItem(R.id.search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView: SearchView = item.actionView as? SearchView ?: return
        searchView.setOnQueryTextListener(DebouncingQueryTextListener(viewLifecycleOwner.lifecycle) { queryMessage ->
            dispatchQueryMessage(queryMessage)
        })
    }

    private fun dispatchQueryMessage(searchQuery: String) {
        searchViewModel.handleEvent(SearchEvent.OnQueryTextChange(searchQuery))
        // When we change the query we don't want to fetch wrong pages, imagine this scenario
        // we searched for a term "A", and scrolled to page 6, now we reset the
        // query to term "B", and now we don't want to fetch page 7, we want page 2,
        // that's why after every dispatchQueryMessage we also reset the page.
        scrollListener?.resetCurrentPage()
    }

    private fun dispatchMoreData(query: String, newPage: Int) {
        if (query.isNotBlank())
            searchViewModel.handleEvent(SearchEvent.OnPagingLoadMore(query, newPage))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cleanup
        binding.searchRecyclerView.adapter = null
        searchMenu = null
        scrollListener = null
    }
}