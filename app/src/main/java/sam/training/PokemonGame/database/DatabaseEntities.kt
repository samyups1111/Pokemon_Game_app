package sam.training.PokemonGame.database

import androidx.room.Entity
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.domain.Sprites

@Entity
data class DatabasePokemon(
    val id: Int,
    val order: Int,
    val name : String,
    val sprites : Sprites
)

fun List<DatabasePokemon>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            id = it.id,
            order = it.order,
            name = it.name,
            sprites = it.sprites)
    }
}
