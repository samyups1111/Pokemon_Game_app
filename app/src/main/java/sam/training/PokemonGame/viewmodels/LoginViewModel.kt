package sam.training.PokemonGame.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import sam.training.PokemonGame.firebase.FirebaseUserLiveData
import kotlin.random.Random

class LoginViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}