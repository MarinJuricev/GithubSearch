package com.evolutio.presentation.feature.private_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.databinding.FragmentPrivateUserBinding
import com.evolutio.presentation.feature.user_detail.UserDataAdapter
import javax.inject.Inject

class PrivateUserDataFragment : BaseFragment() {

    @Inject
    lateinit var privateUserViewModel: PrivateUserViewModel

    private lateinit var binding: FragmentPrivateUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentPrivateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        privateUserViewModel.handleEvent(PrivateUserEvent.OnStart)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        privateUserViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (!isLoading)
                binding.pbPrivateDataProgress.visibility = View.GONE
        })

        privateUserViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        val userDataAdapter = UserDataAdapter()
        binding.rvPrivateData.adapter = userDataAdapter

        privateUserViewModel.userData.observe(viewLifecycleOwner, Observer { userData ->
            userDataAdapter.submitList(userData)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPrivateData.adapter = null
    }
}