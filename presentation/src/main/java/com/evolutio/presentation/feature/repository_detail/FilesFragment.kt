package com.evolutio.presentation.feature.repository_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.R
import com.evolutio.presentation.databinding.FragmentCommitsBinding
import com.evolutio.presentation.databinding.FragmentContributorsBinding
import com.evolutio.presentation.databinding.FragmentFilesBinding


class FilesFragment : Fragment() {

    private lateinit var binding: FragmentFilesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

}