package com.evolutio.presentation.feature.private_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.databinding.FragmentPrivateUserBinding

class PrivateUserDataFragment : BaseFragment() {

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

}