package sam.training.PokemonGame.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokeDao {
    @Query("Select * from DatabasePokemon")
    fun getVideos(): LiveData<List<DatabasePokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabasePokemon)
}


@Database(entities = [DatabasePokemon::class], version =1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val pokeDao : PokeDao
}

private lateinit var INSTANCE : PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase {
    synchronized(PokemonDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PokemonDatabase::class.java,
                "").build()
        }
    }
    return INSTANCE
}