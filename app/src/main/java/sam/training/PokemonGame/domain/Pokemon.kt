package sam.training.PokemonGame.domain


//https://pokeapi.co/api/v2/pokemon-form/1/
data class Pokemon(
    val id: Int,
    val order: Int,
    val name : String,
    val front_default : String,
    val back_default : String
)


