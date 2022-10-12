package fi.shaynek.parliamentfinland.app

import android.app.Application
import android.os.Build
import androidx.work.*
import fi.shaynek.parliamentfinland.background.RefreshDataWorker
import fi.shaynek.parliamentfinland.data.database.db.ParliamentDatabase
import fi.shaynek.parliamentfinland.data.network.ParliamentApiClient
import fi.shaynek.parliamentfinland.data.repositories.CommentsRepository
import fi.shaynek.parliamentfinland.data.repositories.MembersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


/**
 * This class is singleton - only one instance is guaranteed to ever exist.
 * It also contains the work request implementation which defines how and when work should be run
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 3.0
 * @since 29.09.2022
 */

class MainApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private val database: ParliamentDatabase by lazy { ParliamentDatabase.getDatabase(this) }

    val membersRepository: MembersRepository by lazy{
        MembersRepository(
            membersBasicDataDao = database.membersBasicDataDao,
            membersExtraDataDao = database.membersExtraDataDao,
            apiClient = ParliamentApiClient.retrofitService
        )

    }

    val commentsRepository: CommentsRepository by lazy {
        CommentsRepository(
            database.commentsDao,
            database.reactionsDao
        )

    }

    /**
     * onCreate is called before the first screen is shown to the user.
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }



    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork() {

        val constraints= Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }
}