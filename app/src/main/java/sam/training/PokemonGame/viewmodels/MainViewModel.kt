package sam.training.PokemonGame.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.network.NetworkPokeContainer
import sam.training.PokemonGame.network.NetworkPokemon
import sam.training.PokemonGame.network.PokemonApi
import sam.training.PokemonGame.network.asDomainModel

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val TAG = "ViewModel"
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status : LiveData<PokemonApiStatus>
        get() = _status
    private val _poke = MutableLiveData<Pokemon>()
    val poke : LiveData<Pokemon>
        get() = _poke
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _player1Team = MutableLiveData<List<Pokemon>>()
    val player1Team : LiveData<List<Pokemon>>
    get() = _player1Team
    private val _player2Team = MutableLiveData<List<Pokemon>>()
    val player2Team : LiveData<List<Pokemon>>
    get() = _player2Team
    private val _currentPoke = MutableLiveData<Pokemon>()
    val currentPoke : LiveData<Pokemon>
        get() = _currentPoke
    private val _allPokemon = MutableLiveData<List<Pokemon>>()
    val allPokemon : LiveData<List<Pokemon>>
        get() = _allPokemon

    init {
        getPokemon()
        getAllPokemons()
    }

    private fun getPokemon() {
        coroutineScope.launch {
            fun randomNum() = (1..150).random()
            val getPokemonsDeferred = PokemonApi.retrofitService.getRandomPokemon(randomNum())
            val team1 = mutableListOf<Deferred<Pokemon>>()
            val team2 = mutableListOf<Deferred<Pokemon>>()
            for (i in 0 until 6) {
                team1.add(PokemonApi.retrofitService.getRandomPokemon(randomNum()))
                team2.add(PokemonApi.retrofitService.getRandomPokemon(randomNum()))
            }

            try {
                _status.value = PokemonApiStatus.LOADING
                val result = getPokemonsDeferred.await()
                val team1Result = team1.awaitAll()
                val team2Result = team2.awaitAll()
                _status.value = PokemonApiStatus.DONE
                _poke.value = result
                _player1Team.value = team1Result
                _player2Team.value = team2Result
                Log.d(TAG, "player1Team = $player1Team" )
            } catch (t: Throwable) {
                _status.value = PokemonApiStatus.ERROR
                Log.d(TAG, "Error: player1Team = $player1Team" )
            }
        }
    }

    fun getAllPokemons() {
        coroutineScope.launch {
            val pokemonList = mutableListOf<Deferred<NetworkPokemon>>()
            for (i in 1..151) {
                pokemonList.add(PokemonApi.retrofitService.getPokemon(i))
            }
            try {
                val result = pokemonList.awaitAll()
                val container = NetworkPokeContainer(result)
                _allPokemon.value = container.asDomainModel()
                Log.d(TAG, "allPokemon: " + result.size.toString())
            } catch (t: Throwable) {

            }
        }
    }

    fun selectedPoke(pokemon: Pokemon) {
        _currentPoke.value = pokemon
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}