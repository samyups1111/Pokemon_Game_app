package sam.training.PokemonGame.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import sam.training.PokemonGame.database.getDatabase
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.repository.PokemonRepository

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val pokeRepository = PokemonRepository(database)
    private fun randomNum() = (0..150).random()
    private val _player1Team = MutableLiveData<MutableList<Pokemon>>()
    val player1Team : LiveData<MutableList<Pokemon>>
        get() = _player1Team
    private val _player2Team = MutableLiveData<MutableList<Pokemon>>()
    val player2Team : LiveData<MutableList<Pokemon>>
        get() = _player2Team
    private val _currentPoke = MutableLiveData<Pokemon>()
    val currentPoke : LiveData<Pokemon>
        get() = _currentPoke

    init {
        viewModelScope.launch {
            if (pokemonList.value?.size == 0) pokeRepository.refreshPokemonList()
        }
    }

    val pokemonList = pokeRepository.pokemonList

    fun makeRandomTeams() {
        val tempList1 = mutableListOf<Pokemon>()
        val tempList2 = mutableListOf<Pokemon>()
        for (i in 1..6) {
            tempList1.add(pokemonList.value?.random()!!)
            tempList2.add(pokemonList.value?.random()!!)
        }
        _player1Team.value = tempList1
        _player2Team.value = tempList2
    }

    fun selectedPoke(pokemon: Pokemon) {
        _currentPoke.value = pokemon
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}