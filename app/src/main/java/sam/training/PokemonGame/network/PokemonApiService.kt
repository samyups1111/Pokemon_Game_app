package sam.training.PokemonGame.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import sam.training.PokemonGame.domain.*

const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface PokemonApiService {

    @GET("pokemon-form/{randomNum}")
    fun getRandomPokemon(@Path("randomNum") randomNum :Int):
            Deferred<Pokemon>

    @GET("pokemon-form/{i}")
    fun getPokemon(@Path("i") i : Int):
            Deferred<NetworkPokemon>
}

object PokemonApi {
    val retrofitService : PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}
