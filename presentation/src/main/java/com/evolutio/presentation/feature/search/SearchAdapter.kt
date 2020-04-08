package com.evolutio.presentation.feature.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.PaginationStatus
import com.evolutio.domain.model.search.Repository
import com.evolutio.presentation.databinding.ItemErrorBinding
import com.evolutio.presentation.databinding.ItemLoadingBinding
import com.evolutio.presentation.databinding.ItemRepositoryBinding
import com.evolutio.presentation.shared.views.BaseViewHolder

class SearchAdapter(
    private val goToRepositoryDetail: (Repository) -> Unit = {},
    private val goToUserDetail: (Repository, View) -> Unit
) :
    ListAdapter<AdapterItem, BaseViewHolder<AdapterItem>>(AdapterDiffUtilCallback()) {

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_REPOSITORY = 1
        const val VIEW_TYPE_ERROR = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AdapterItem> {
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                val itemLoadingBinding =
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                LoadingViewHolder(itemLoadingBinding)
            }
            VIEW_TYPE_ERROR -> {
                val itemErrorBinding =
                    ItemErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                ErrorViewHolder(itemErrorBinding)
            }
            else -> {
                val itemRepositoryBinding =
                    ItemRepositoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                SearchViewHolder(itemRepositoryBinding, goToRepositoryDetail, goToUserDetail)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AdapterItem>, position: Int) {
        if (currentList.isNotEmpty()) {
            getItem(position)?.let { repositoryItem ->
                holder.bind(repositoryItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Repository -> VIEW_TYPE_REPOSITORY
            is PaginationStatus.Error -> VIEW_TYPE_ERROR
            else -> VIEW_TYPE_LOADING
        }
    }
}

class SearchViewHolder(
    private val binding: ItemRepositoryBinding,
    private val goToRepositoryDetail: (Repository) -> Unit = {},
    private val goToUserDetail: (Repository, View) -> Unit
) :
    BaseViewHolder<AdapterItem>(binding.root) {

    override fun bind(data: AdapterItem) {
        if (data is Repository) {
            binding.ivThumbnail.apply {
                transitionName = data.transitionName
                Glide.with(this.context)
                    .applyDefaultRequestOptions(RequestOptions())
                    .load(data.thumbnail)
                    .placeholder(ColorDrawable(Color.LTGRAY))
                    .dontTransform()
                    .into(this)
            }

            binding.tvStarCount.text = data.starsCount
            binding.tvRepoDescription.text = data.repositoryInfo
            binding.tvForkCount.text = data.forkCount
            binding.tvIssuesCount.text = data.issuesCount
            binding.tvWatcherCount.text = data.watcherCount

            binding.root.setOnClickListener {
                goToRepositoryDetail(data)
            }

            binding.ivThumbnail.setOnClickListener {
                goToUserDetail(data, binding.ivThumbnail)
            }
        }
    }
}

class LoadingViewHolder(private val binding: ItemLoadingBinding) :
    BaseViewHolder<AdapterItem>(binding.root) {

    override fun bind(data: AdapterItem) {
        binding.progressBar.visibility = View.VISIBLE
    }
}

class ErrorViewHolder(private val binding: ItemErrorBinding) :
    BaseViewHolder<AdapterItem>(binding.root) {

    override fun bind(data: AdapterItem) {
        if (data is PaginationStatus.Error)
            binding.tvError.text = data.errorMessage
    }
}

