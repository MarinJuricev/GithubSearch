package com.evolutio.presentation.feature.repository_detail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evolutio.domain.model.search.Repository
import com.evolutio.presentation.databinding.FragmentRepositoryDetailBinding
import com.evolutio.presentation.ext.doesDeviceHaveABrowser
import com.google.android.material.tabs.TabLayoutMediator


class RepositoryDetailFragment : Fragment() {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentRepositoryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData(args.repository)
    }

    private fun bindData(repository: Repository) {
        binding.detailInfoView.tvStarCount.text = repository.starsCount
        binding.detailInfoView.tvRepoDescription.text = repository.repositoryInfo
        binding.detailInfoView.tvForkCount.text = repository.forkCount
        binding.detailInfoView.tvIssuesCount.text = repository.issuesCount
        binding.detailInfoView.tvWatcherCount.text = repository.watcherCount
        binding.detailInfoView.tvRepoCreatedAtInfo.text = repository.created
        binding.detailInfoView.tvRepoLastUpdatedAtInfo.text = repository.lastUpdated
        binding.detailInfoView.tvProgrammingLanguageInfo.text = repository.programmingLanguage

        Glide.with(binding.detailInfoView.ivThumbnail.context)
            .applyDefaultRequestOptions(RequestOptions())
            .load(repository.thumbnail)
            .placeholder(ColorDrawable(Color.LTGRAY))
            .dontTransform()
            .into(binding.detailInfoView.ivThumbnail)

        binding.detailInfoView.ivThumbnail.apply {
            transitionName = repository.transitionName
            setOnClickListener {
                val extras = FragmentNavigator.Extras.Builder()
                extras.addSharedElement(this, this.transitionName)
                val build = extras.build()

                findNavController().navigate(
                    RepositoryDetailFragmentDirections.actionRepositoryDetailFragmentToUserDetailFragment(
                        repository
                    ),
                    build
                )
            }
        }

        binding.detailInfoView.btnOpenInBrowser.setOnClickListener {
            if (requireContext().doesDeviceHaveABrowser(repository.projectHtmlUrl)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                i.data = Uri.parse(repository.projectHtmlUrl)
                requireContext().startActivity(i)
            }
        }

        bindAdapter()
    }

    private fun bindAdapter() {
        val tabTitles = generateRepositoriesInfoTitles()
        val repositoryInfoAdapter = RepositoryDetailAdapter(this, tabTitles)

        binding.additionalInfoViewPager.adapter = repositoryInfoAdapter

        TabLayoutMediator(binding.additionalInfoTabLayout, binding.additionalInfoViewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabTitles[position]
            }).attach()
    }

    private fun generateRepositoriesInfoTitles(): List<String> {
        return listOf(
            "Readme",
            "Files",
            "Commits",
            "Releases",
            "Contributors"
        )

    }
}