package sam.training.PokemonGame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import sam.training.PokemonGame.viewmodels.MainViewModel
import sam.training.PokemonGame.R
import sam.training.PokemonGame.bindImage
import sam.training.PokemonGame.bindStatus

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

    val mainViewModel by viewModels<MainViewModel>()

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
    }

    private fun initObservers() {
        mainViewModel.status.observe(this, Observer { status ->
            bindStatus(statusImgView, status)
        })

        mainViewModel.poke.observe(this, Observer { it ->
            bindImage(imageView, it.sprites.front_default)
        })

        mainViewModel.player1Team.observe(this, Observer { team1List ->
            bindImage(pokeballA1, team1List[0].sprites.front_default)
            bindImage(pokeballA2, team1List[1].sprites.front_default)
            bindImage(pokeballA3, team1List[2].sprites.front_default)
            bindImage(pokeballA4, team1List[3].sprites.front_default)
            bindImage(pokeballA5, team1List[4].sprites.front_default)
            bindImage(pokeballA6, team1List[5].sprites.front_default)
        })

        mainViewModel.player2Team.observe(this, Observer { team2List ->
            bindImage(pokeballB1, team2List[0].sprites.front_default)
            bindImage(pokeballB2, team2List[1].sprites.front_default)
            bindImage(pokeballB3, team2List[2].sprites.front_default)
            bindImage(pokeballB4, team2List[3].sprites.front_default)
            bindImage(pokeballB5, team2List[4].sprites.front_default)
            bindImage(pokeballB6, team2List[5].sprites.front_default)

            pokeballB1.setOnClickListener {
                bindImage(imageView, team2List[0].sprites.front_default)
                pokeName.text = team2List[0].name
                mainViewModel.selectedPoke(team2List[0])
            }

            pokeballB2.setOnClickListener {
                bindImage(imageView, team2List[1].sprites.front_default)
                pokeName.text = team2List[1].name
                mainViewModel.selectedPoke(team2List[1])
            }

            pokeballB3.setOnClickListener {
                bindImage(imageView, team2List[2].sprites.front_default)
                pokeName.text = team2List[2].name
                mainViewModel.selectedPoke(team2List[2])
            }

            pokeballB4.setOnClickListener {
                bindImage(imageView, team2List[3].sprites.front_default)
                pokeName.text = team2List[3].name
                mainViewModel.selectedPoke(team2List[3])
            }

            pokeballB5.setOnClickListener {
                bindImage(imageView, team2List[4].sprites.front_default)
                pokeName.text = team2List[4].name
                mainViewModel.selectedPoke(team2List[4])
            }

            pokeballB6.setOnClickListener {
                bindImage(imageView, team2List[5].sprites.front_default)
                pokeName.text = team2List[5].name
                mainViewModel.selectedPoke(team2List[5])
            }
        })


        mainViewModel.currentPoke.observe(this, Observer { pokemon ->
            switchButton.setOnClickListener {
                bindImage(mainPoke2, pokemon.sprites.back_default)
            }
        })



    }
}