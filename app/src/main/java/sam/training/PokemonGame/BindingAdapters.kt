package sam.training.PokemonGame

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import sam.training.PokemonGame.viewmodels.PokemonApiStatus

fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.pokeball)
                .error(R.drawable.pokeballdark))
            .into(imgView)
    }
}

fun bindStatus(imgView: ImageView, status: PokemonApiStatus?) {
    when (status) {
        PokemonApiStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.pokeball)
        }
        PokemonApiStatus.ERROR -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.pokeballdark)
        }
        PokemonApiStatus.DONE -> {
            imgView.visibility = View.GONE
        }
    }
}