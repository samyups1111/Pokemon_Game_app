package sam.training.PokemonGame.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sam.training.PokemonGame.R
import sam.training.PokemonGame.bindImage
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.viewmodels.MainViewModel

class ViewHolderPlayer2(view: View, mainViewModel: MainViewModel): RecyclerView.ViewHolder(view) {

    private val pokemonImageView: ImageView = view.findViewById(R.id.main_poke2)

    fun bind(currentPokemon: Pokemon) {

        bindImage(pokemonImageView, currentPokemon.back_default)

        /**dogNameTextView.setOnClickListener {
            val navController = Navigation.findNavController(itemView)
            navController.navigate(R.id.action_dogNamesFragment_to_dogInfoFragment)
            mainViewModel.setDog(dog)
            mainViewModel.setCurrentIndex(position)
        }*/
    }
}