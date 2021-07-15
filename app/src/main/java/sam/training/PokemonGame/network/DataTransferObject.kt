package sam.training.PokemonGame.network

import sam.training.PokemonGame.database.DatabasePokemon
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.domain.Sprites

data class NetworkPokeContainer(val pokemons : List<NetworkPokemon>)

data class NetworkPokemon(
    val id: Int,
    val order: Int,
    val name : String,
    val sprites : Sprites
)

fun NetworkPokeContainer.asDomainModel() : List<Pokemon> {
    return pokemons.map {
        Pokemon(
            id = it.id,
            order = it.order,
            name = it.name,
            sprites = it.sprites
        )
    }
}

fun NetworkPokeContainer.asDatabasemodel() : List<DatabasePokemon> {
    return pokemons.map {
        DatabasePokemon(
            id = it.id,
            order = it.order,
            name = it.name,
            sprites = it.sprites
        )
    }
}