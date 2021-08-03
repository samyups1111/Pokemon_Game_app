package sam.training.PokemonGame.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sam.training.PokemonGame.R
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.viewmodels.MainViewModel

class RecyclerViewAdapterPlayer2(val mainViewModel: MainViewModel)  : RecyclerView.Adapter<ViewHolderPlayer2>() {

    var currentPokeList = ArrayList<Pokemon>(3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPlayer2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_player2, parent, false)
        return ViewHolderPlayer2(view, mainViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolderPlayer2, position: Int) {
        val currentPokemon= currentPokeList[position]
        holder.bind(currentPokemon)
    }

    override fun getItemCount(): Int {
        return 3
    }

    fun updateList(currentPokemon: Pokemon) {
        val list = ArrayList<Pokemon>(3)
        val placeHolder1 = Pokemon(0, 0, "n/a", "n/a", "n/a")
        val placeHolder2 = Pokemon(0, 0, "n/a", "n/a", "n/a")
        list.add(currentPokemon)
        list.add(placeHolder1)
        list.add(placeHolder2)
        currentPokeList = list
        notifyDataSetChanged()
    }
}