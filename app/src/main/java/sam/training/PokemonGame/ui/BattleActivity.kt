package sam.training.PokemonGame.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sam.training.PokemonGame.viewmodels.MainViewModel
import sam.training.PokemonGame.R
import sam.training.PokemonGame.bindImage
import sam.training.PokemonGame.domain.Pokemon
import sam.training.PokemonGame.recyclerview.RecyclerViewAdapterPlayer2
import sam.training.PokemonGame.util.sendNotification

class BattleActivity : AppCompatActivity() {

    private lateinit var pokeDisplayImageView : ImageView
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
    private lateinit var recyclerView : RecyclerView
    private lateinit var pokeAdapter2 : RecyclerViewAdapterPlayer2

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application)).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        initVariables()
        initRecyclerView()
        initItemTouchHelper(recyclerView)
        createChannel("test", "Test")
        setSwitchButton()
        makeRandomTeams()
        loadPlayer1Team()
        loadPlayer2Team()
    }

    private fun initVariables() {
        pokeDisplayImageView = findViewById(R.id.poke_display_image_view)
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

        recyclerView = findViewById(R.id.recycler_view_team2)
        pokeName = findViewById(R.id.poke_name)
        switchButton = findViewById(R.id.switch_button)
    }

    private fun setSwitchButton() {
        switchButton.setOnClickListener {
            mainViewModel.currentPoke.value?.let { currentPokemon -> pokeAdapter2.updateList(currentPokemon) }
            mainViewModel.notificationManager.sendNotification("Hi this is a test", application)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        pokeAdapter2 = RecyclerViewAdapterPlayer2(mainViewModel)
        recyclerView.adapter = pokeAdapter2
        pokeAdapter2.updateList(Pokemon(0, 0, "n/a", "n/a", "n/a"))
    }

    private fun makeRandomTeams() {
        mainViewModel.pokemonList.observe(this, Observer { pokemonList ->
            mainViewModel.makeRandomTeams()
        })
    }

    private fun loadPlayer1Team() {
        mainViewModel.player1Team.observe(this, Observer { Team1 ->
            bindImage(pokeballA1, Team1[0].front_default)
            bindImage(pokeballA2, Team1[1].front_default)
            bindImage(pokeballA3, Team1[2].front_default)
            bindImage(pokeballA4, Team1[3].front_default)
            bindImage(pokeballA5, Team1[4].front_default)
            bindImage(pokeballA6, Team1[5].front_default)
        })
    }

    private fun loadPlayer2Team() {
        mainViewModel.player2Team.observe(this, Observer { Team2 ->
            bindImage(pokeballB1, Team2[0].front_default)
            bindImage(pokeballB2, Team2[1].front_default)
            bindImage(pokeballB3, Team2[2].front_default)
            bindImage(pokeballB4, Team2[3].front_default)
            bindImage(pokeballB5, Team2[4].front_default)
            bindImage(pokeballB6, Team2[5].front_default)

            setTeam2ClickListeners(Team2)
        })
    }

    private fun setTeam2ClickListeners(team2: List<Pokemon>) {
        pokeballB1.setOnClickListener {
            bindPokeDisplayImageView(team2[0])
        }

        pokeballB2.setOnClickListener {
            bindPokeDisplayImageView(team2[1])
        }

        pokeballB3.setOnClickListener {
            bindPokeDisplayImageView(team2[2])
        }

        pokeballB4.setOnClickListener {
            bindPokeDisplayImageView(team2[3])
        }

        pokeballB5.setOnClickListener {
            bindPokeDisplayImageView(team2[4])
        }

        pokeballB6.setOnClickListener {
            bindPokeDisplayImageView(team2[5])
        }
    }

    private fun bindPokeDisplayImageView(pokemon: Pokemon) {
        bindImage(pokeDisplayImageView, pokemon.front_default)
        pokeName.text = pokemon.name
        mainViewModel.selectedPoke(pokemon)
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Testing Notification system"

            val notificationManager = this.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun initItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT or
                    ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                recyclerView.adapter?.notifyItemMoved(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentPokemonListPlayer2 = pokeAdapter2.currentPokeList
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}