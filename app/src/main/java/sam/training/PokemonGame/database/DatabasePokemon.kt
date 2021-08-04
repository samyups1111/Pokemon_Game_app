package sam.training.PokemonGame.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import sam.training.PokemonGame.domain.Pokemon

@Entity
data class DatabasePokemon(
    @PrimaryKey
    val id: Int,
    val order: Int,
    val name : String,
    val front_default : String,
    val back_default : String
)

fun List<DatabasePokemon>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            id = it.id,
            order = it.order,
            name = it.name,
            front_default = it.front_default,
            back_default = it.back_default
        )}
}
