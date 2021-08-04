package sam.training.PokemonGame.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private lateinit var recyclerView : RecyclerView
    private lateinit var pokeAdapter2 : RecyclerViewAdapterPlayer2

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(this.application)).get(MainViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)
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

        recyclerView = findViewById(R.id.recycler_view_team2)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        pokeAdapter2 = RecyclerViewAdapterPlayer2(mainViewModel)
        recyclerView.adapter = pokeAdapter2
        pokeAdapter2.updateList(Pokemon(0, 0, "n/a", "n/a", "n/a"))

        pokeName = findViewById(R.id.poke_name)
        switchButton = findViewById(R.id.switch_button)

        initObservers()
        initItemTouchHelper(recyclerView)
        createChannel("test", "Test")


        switchButton.setOnClickListener {

            Log.d("TAG", "MA Team1 Pokemon 1" + mainViewModel.player1Team.value?.get(0)?.name)
            //bindImage(mainPoke2, mainViewModel.currentPoke.value?.back_default)
            mainViewModel.currentPoke.value?.let { currentPokemon -> pokeAdapter2.updateList(currentPokemon) }


            mainViewModel.notificationManager.sendNotification("Hi this is a test", application)
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
                //mainViewModel.delete(currentPokemonListPlayer2[viewHolder.adapterPosition])
                //mainAdapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}