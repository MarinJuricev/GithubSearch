package com.evolutio.presentation.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evolutio.presentation.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestLogin.setOnClickListener {
            loginViewModel.handleEvent(LoginEvent.OnLoginStart)
        }

        binding.btnClearToken.setOnClickListener {
            loginViewModel.handleEvent(LoginEvent.OnClearToken)
        }

        binding.btnGetPrivateUserData.setOnClickListener {
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToPrivateRepositoryFragment())
        }
    }

    override fun onStart() {
        super.onStart()

    }
}