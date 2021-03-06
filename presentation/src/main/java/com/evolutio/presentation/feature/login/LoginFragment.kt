package com.evolutio.presentation.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.evolutio.presentation.BaseFragment
import com.evolutio.presentation.R
import com.evolutio.presentation.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        loginViewModel.handleEvent(LoginEvent.OnAccessTokenStatus)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestLogin.setOnClickListener {
            loginViewModel.handleEvent(LoginEvent.OnLoginStart)
        }

        binding.btnLogout.setOnClickListener {
            loginViewModel.handleEvent(LoginEvent.OnLogout)
        }

        binding.btnGetPrivateUserData.setOnClickListener {
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToPrivateRepositoryFragment())
        }

        observeViewModelData()
    }

    private fun observeViewModelData() {
        loginViewModel.tokenDeletion.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(R.string.token_delete), Toast.LENGTH_SHORT)
                .show()
        })

        loginViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.tokenAvailability.observe(viewLifecycleOwner, Observer { tokenAvailability ->
            when (tokenAvailability) {
                TokenAvailability.Available -> binding.tvAccessTokenPresent.text = getString(
                    R.string.token_status, getString(R.string.available)
                )
                TokenAvailability.NotAvailable -> binding.tvAccessTokenPresent.text = getString(
                    R.string.token_status, getString(R.string.token_not_available)
                )
            }
        })
    }

}