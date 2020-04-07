package com.evolutio.presentation.feature.user_detail

import androidx.recyclerview.widget.DiffUtil
import com.evolutio.domain.model.user.UserItem

class UserItemDiffUtilCallback : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return (oldItem.itemContent == newItem.itemContent)
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }
}