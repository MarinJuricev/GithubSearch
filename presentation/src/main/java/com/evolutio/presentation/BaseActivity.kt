package com.evolutio.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.evolutio.domain.shared.CODE_QUERY_PARAMETER
import com.evolutio.domain.shared.ERROR_PARAMETER
import com.evolutio.domain.shared.REDIRECT_URI
import com.evolutio.presentation.feature.login.LoginEvent
import com.evolutio.presentation.feature.login.LoginViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class BaseActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setupNavController()
    }

    private fun setupNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            val code = uri.getQueryParameter(CODE_QUERY_PARAMETER)

            if (!code.isNullOrBlank())
                loginViewModel.handleEvent(LoginEvent.OnGetAccessToken(code))

        } else if (uri?.getQueryParameter(ERROR_PARAMETER) != null) {
            Toast.makeText(
                this,
                uri.getQueryParameter("ERROR: $ERROR_PARAMETER"),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}