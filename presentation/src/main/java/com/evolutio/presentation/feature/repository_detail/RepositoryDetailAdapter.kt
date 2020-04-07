package com.evolutio.presentation.feature.repository_detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RepositoryDetailAdapter(
    fragment: Fragment,
    private val items: List<String>
) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReadmeFragment()
            1 -> FilesFragment()
            2 -> CommitsFragment()
            3 -> ReleasesFragment()
            4 -> ContributorsFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = items.size
}