package fi.shaynek.parliamentfinland.background

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import fi.shaynek.parliamentfinland.data.database.db.ParliamentDatabase.Companion.getDatabase
import fi.shaynek.parliamentfinland.data.network.ParliamentApiClient
import fi.shaynek.parliamentfinland.data.repositories.MembersRepository
import kotlinx.coroutines.currentCoroutineContext
import retrofit2.HttpException

/**
 * This is the worker class which pre-fetches the member basic and extra data in the background
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 3.0
 * @since 09.10.2022
 */

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME  = "fi.shaynek.parliamentfinland.background.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = MembersRepository(database.membersBasicDataDao, database.membersExtraDataDao, ParliamentApiClient.retrofitService)


        try {
            repository.refreshData()
            Toast.makeText(applicationContext, "Hello programming, please be good to us", Toast.LENGTH_SHORT).show()
        }catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}