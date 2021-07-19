package sam.training.PokemonGame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sam.training.PokemonGame.viewmodels.MainViewModel
import sam.training.PokemonGame.R
import sam.training.PokemonGame.bindImage

class MainActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var statusImgView : ImageView

    private lateinit var pokeballA1 : ImageView
    private lateinit var pokeballA2 : ImageView
    private lateinit var pokeballA3 : ImageView
    private lateinit var pokeballA4 : ImageView
    private lateinit var pokeballA5 : ImageView
    private lateinit var pokeballA6 : ImageView

    private lateinit var pokeballB1 : ImageView
    private lateinit var pokeballB2 : ImageView
    private lateinit var pokeballB3 : ImageView
    private lateinit var pokeballB4 : ImageView
    private lateinit var pokeballB5 : ImageView
    private lateinit var pokeballB6 : ImageView

    private lateinit var pokeName : TextView
    private lateinit var switchButton : Button
    private lateinit var mainPoke2 : ImageView

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application)).get(MainViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image_view)
        statusImgView = findViewById(R.id.status_img_view)
        pokeballA1 = findViewById(R.id.pokeball_a1)
        pokeballA2 = findViewById(R.id.pokeball_a2)
        pokeballA3 = findViewById(R.id.pokeball_a3)
        pokeballA4 = findViewById(R.id.pokeball_a4)
        pokeballA5 = findViewById(R.id.pokeball_a5)
        pokeballA6 = findViewById(R.id.pokeball_a6)

        pokeballB1 = findViewById(R.id.pokeball_b1)
        pokeballB2 = findViewById(R.id.pokeball_b2)
        pokeballB3 = findViewById(R.id.pokeball_b3)
        pokeballB4 = findViewById(R.id.pokeball_b4)
        pokeballB5 = findViewById(R.id.pokeball_b5)
        pokeballB6 = findViewById(R.id.pokeball_b6)

        pokeName = findViewById(R.id.poke_name)
        switchButton = findViewById(R.id.switch_button)
        mainPoke2 = findViewById(R.id.main_poke2)

        initObservers()


        switchButton.setOnClickListener {

            Log.d("TAG", "MA Team1 Pokemon 1" + mainViewModel.player1Team.value?.get(0)?.name)
            bindImage(mainPoke2, mainViewModel.currentPoke.value?.back_default)
        }

        mainViewModel.pokemonList.observe(this, Observer { pokemonList ->
            mainViewModel.makeRandomTeams()


        })

    }



    private fun initObservers() {
        mainViewModel.player1Team.observe(this, Observer { Team1 ->
            bindImage(pokeballA1, Team1[0].front_default)
            bindImage(pokeballA2, Team1[1].front_default)
            bindImage(pokeballA3, Team1[2].front_default)
            bindImage(pokeballA4, Team1[3].front_default)
            bindImage(pokeballA5, Team1[4].front_default)
            bindImage(pokeballA6, Team1[5].front_default)
        })

        mainViewModel.player2Team.observe(this, Observer { Team2 ->
            bindImage(pokeballB1, Team2[0].front_default)
            bindImage(pokeballB2, Team2[1].front_default)
            bindImage(pokeballB3, Team2[2].front_default)
            bindImage(pokeballB4, Team2[3].front_default)
            bindImage(pokeballB5, Team2[4].front_default)
            bindImage(pokeballB6, Team2[5].front_default)

            pokeballB1.setOnClickListener {
                bindImage(imageView, Team2[0].front_default)
                pokeName.text = Team2[0].name
                mainViewModel.selectedPoke(Team2[0])}

            pokeballB2.setOnClickListener {
                bindImage(imageView, Team2[1].front_default)
                pokeName.text = Team2[1].name
                mainViewModel.selectedPoke(Team2[1])
            }

            pokeballB3.setOnClickListener {
                bindImage(imageView, Team2[2].front_default)
                pokeName.text = Team2[2].name
                mainViewModel.selectedPoke(Team2[2])
            }

            pokeballB4.setOnClickListener {
                bindImage(imageView, Team2[3].front_default)
                pokeName.text = Team2[3].name
                mainViewModel.selectedPoke(Team2[3])
            }

            pokeballB5.setOnClickListener {
                bindImage(imageView, Team2[4].front_default)
                pokeName.text = Team2[4].name
                mainViewModel.selectedPoke(Team2[4])
            }

            pokeballB6.setOnClickListener {
                bindImage(imageView, Team2[5].front_default)
                pokeName.text = Team2[5].name
                mainViewModel.selectedPoke(Team2[5])
            }
        })


    }
}