package sam.training.PokemonGame.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import sam.training.PokemonGame.database.getDatabase
import sam.training.PokemonGame.repository.PokemonRepository

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "Refresh Data Worker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = PokemonRepository(database)

        return try {
            repository.refreshPokemonList()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}