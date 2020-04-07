package com.evolutio.presentation.feature.repository_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.databinding.FragmentContributorsBinding

class ContributorsFragment : Fragment() {

    private lateinit var binding: FragmentContributorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContributorsBinding.inflate(inflater, container, false)
        return binding.root
    }

}