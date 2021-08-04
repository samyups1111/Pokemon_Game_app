package sam.training.PokemonGame.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import sam.training.PokemonGame.database.PokemonDatabase
import sam.training.PokemonGame.database.asDomainModel
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.network.NetworkPokemon
import sam.training.PokemonGame.network.PokemonApi
import sam.training.PokemonGame.network.asDatabasemodel

class PokemonRepository(private val database: PokemonDatabase) {

    val pokemonList : LiveData<List<Pokemon>> = Transformations.map(database.pokeDao.getPokemons()) {
        it.asDomainModel()
    }

    suspend fun refreshPokemonList() {
        withContext(Dispatchers.IO) {
            val pokeList = mutableListOf<Deferred<NetworkPokemon>>()
            for (i in 1..151) {
                pokeList.add(PokemonApi.retrofitService.getPokemon(i))
            }
            val result = pokeList.awaitAll()

            database.pokeDao.insertAll(*result.asDatabasemodel())
            Log.d("TAG", "RP resultList = ${result.size}")
            Log.d("TAG", "RP pokemonList = ${pokemonList.value?.size}")
        }
    }
}