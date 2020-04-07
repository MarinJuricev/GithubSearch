package com.evolutio.presentation.feature.repository_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.databinding.FragmentReadmeBinding

class ReleasesFragment : Fragment() {

    private lateinit var binding: FragmentReadmeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReadmeBinding.inflate(inflater, container, false)
        return binding.root
    }
}