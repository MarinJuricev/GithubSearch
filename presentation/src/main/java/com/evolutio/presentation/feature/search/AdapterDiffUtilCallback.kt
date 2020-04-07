package com.evolutio.presentation.feature.search

import androidx.recyclerview.widget.DiffUtil
import com.evolutio.domain.model.search.AdapterItem
import com.evolutio.domain.model.search.Loading
import com.evolutio.domain.model.search.Repository

class AdapterDiffUtilCallback : DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        if (oldItem is Repository && newItem is Repository) {
            return oldItem.author == newItem.author && oldItem.created == newItem.created
        } else if (oldItem is Loading && newItem is Loading) {
            return oldItem == newItem
        }

        return false
    }

    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        if (oldItem is Repository && newItem is Repository) {
            return oldItem == newItem
        } else if (oldItem is Loading && newItem is Loading) {
            return oldItem == newItem
        }

        return false
    }
}