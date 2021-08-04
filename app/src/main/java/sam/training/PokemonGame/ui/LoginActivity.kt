package sam.training.PokemonGame.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import sam.training.PokemonGame.R
import sam.training.PokemonGame.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    companion object {
        const val SIGN_IN_REQUEST_CODE = 1001 }

    private lateinit var signInButton : Button
    private lateinit var settingsButton : TextView
    private lateinit var battleButton : Button
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setBattleButton()
        setSignInButton()
        setSettingsButton()
        observeAuthenticationState()
    }

    private fun setSignInButton() {
        signInButton = findViewById(R.id.signin_button)

        signInButton.setOnClickListener {

            launchSignInFlow()
        }
    }

    private fun setSettingsButton() {
        settingsButton = findViewById(R.id.settings_button)

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setBattleButton() {
        battleButton = findViewById(R.id.battle_button)

        battleButton.setOnClickListener {
            val intent = Intent(this, BattleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun launchSignInFlow() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            SIGN_IN_REQUEST_CODE
        )
    }

    private fun getFactWithPersonalization(fact: String): String {
        return String.format(
            resources.getString(
                R.string.welcome_message_authed,
                FirebaseAuth.getInstance().currentUser?.displayName,
                Character.toLowerCase(fact[0]) + fact.substring(1)
            )
        )
    }

    private fun observeAuthenticationState() {
        val good = "good"
        val bad = "bad"

        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    battleButton.visibility = View.VISIBLE
                    settingsButton.visibility = View.VISIBLE
                    signInButton.text = "Log Out"
                    signInButton.setOnClickListener {
                        AuthUI.getInstance().signOut(this)
                    }
                }
                else -> {
                    settingsButton.visibility = View.GONE
                    battleButton.visibility = View.GONE
                    signInButton.text = "Sign in"
                    signInButton.setOnClickListener { launchSignInFlow() }
                }
            }
        })
    }
}