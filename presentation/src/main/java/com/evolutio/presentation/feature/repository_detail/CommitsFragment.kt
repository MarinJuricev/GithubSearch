package com.evolutio.presentation.feature.repository_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evolutio.presentation.databinding.FragmentCommitsBinding


class CommitsFragment : Fragment() {

    private lateinit var binding: FragmentCommitsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommitsBinding.inflate(inflater, container, false)
        return binding.root
    }
}