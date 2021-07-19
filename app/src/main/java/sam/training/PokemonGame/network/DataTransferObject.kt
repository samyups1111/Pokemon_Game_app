package sam.training.PokemonGame.network

import androidx.lifecycle.Transformations.map
import sam.training.PokemonGame.database.DatabasePokemon

data class NetworkPokeContainer(val pokemons : List<NetworkPokemon>)

data class NetworkPokemon(
    val id: Int,
    val order: Int,
    val name : String,
    val sprites : Sprites
)

data class Sprites(
    val front_default : String,
    val back_default : String
)

fun List<NetworkPokemon>.asDatabasemodel() : Array<DatabasePokemon> {
    return map {
        DatabasePokemon(
            id = it.id,
            order = it.order,
            name = it.name,
            front_default = it.sprites.front_default,
            back_default = it.sprites.back_default
        )
    }.toTypedArray()
}