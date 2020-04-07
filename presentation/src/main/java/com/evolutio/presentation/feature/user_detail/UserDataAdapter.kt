package com.evolutio.presentation.feature.user_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evolutio.domain.model.user.UserItem
import com.evolutio.presentation.databinding.ItemUserBinding

class UserDataAdapter : ListAdapter<UserItem, UserViewHolder>(UserItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemLoadingBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UserViewHolder(itemLoadingBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (currentList.isNotEmpty()) {
            getItem(position)?.let { userItem ->
                holder.bind(userItem)
            }
        }
    }
}

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: UserItem) {
        val context = binding.tvFieldData.context

        val stringTitleId = context.resources.getIdentifier(
            data.itemTitle,
            "string",
            context.packageName
        )
        binding.tvFieldDescription.text = context.getString(stringTitleId)
        binding.tvFieldData.text = data.itemContent
    }

}