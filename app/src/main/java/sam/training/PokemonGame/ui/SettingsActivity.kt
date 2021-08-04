package sam.training.PokemonGame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import sam.training.PokemonGame.R
import sam.training.PokemonGame.viewmodels.LoginViewModel

class SettingsActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        checkAuth()
    }

    private fun checkAuth() {
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> Log.i("TAG", "Authenticated")
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}