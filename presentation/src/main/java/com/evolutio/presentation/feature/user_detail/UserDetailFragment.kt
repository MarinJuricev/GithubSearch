package com.evolutio.presentation.feature.user_detail


import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.evolutio.domain.model.search.Repository
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.databinding.FragmentUserDetailBinding
import com.evolutio.presentation.ext.doesDeviceHaveABrowser
import com.evolutio.presentation.ext.generateDividerDecoration
import com.evolutio.presentation.feature.repository_detail.RepositoryDetailFragmentArgs
import javax.inject.Inject

class UserDetailFragment : BaseFragment() {

    @Inject
    lateinit var userVIewModel: UserDetailViewModel

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var userDataAdapter: UserDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userVIewModel.handleEvent(UserDetailEvent.OnGetUserData(args.repository.author))

        bindRepositoryData(args.repository)
        setupRecyclerView()
        observeUserData()
        observeLoading()
        observeError()
    }

    private fun observeLoading() {
        userVIewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading)
                binding.pbUserData.visibility = View.VISIBLE
            else
                binding.pbUserData.visibility = View.GONE
        })
    }

    private fun observeError() {
        userVIewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        userDataAdapter = UserDataAdapter()
        binding.rvUserData.adapter = userDataAdapter
        binding.rvUserData.apply {
            adapter = userDataAdapter
            addItemDecoration(context.generateDividerDecoration())
        }
    }

    private fun bindRepositoryData(repository: Repository) {
        binding.userDetailInformation.btnOpenInBrowser.setOnClickListener {
            if (requireContext().doesDeviceHaveABrowser(repository.ownerHtmlUrl)) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.data = Uri.parse(repository.ownerHtmlUrl)
                requireContext().startActivity(intent)
            }
        }

        binding.userDetailInformation.tvUserName.text = repository.author
        binding.userDetailInformation.ivThumbnail.transitionName = repository.transitionName

        Glide.with(this)
            .load(repository.thumbnail)
            .dontTransform()
            .listener(object :
                RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.userDetailInformation.ivThumbnail)
    }

    private fun observeUserData() {
        userVIewModel.userData.observe(viewLifecycleOwner, Observer { userData ->
            userDataAdapter.submitList(userData)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvUserData.adapter = null
    }
}