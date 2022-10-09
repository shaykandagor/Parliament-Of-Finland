package fi.shaynek.parliamentfinland.app

import android.app.Application
import fi.shaynek.parliamentfinland.data.database.db.ParliamentDatabase
import fi.shaynek.parliamentfinland.data.network.ParliamentApiClient
import fi.shaynek.parliamentfinland.data.repositories.CommentsRepository
import fi.shaynek.parliamentfinland.data.repositories.MembersRepository

/**
 * This class is singleton - only one instance is guaranteed to ever exist.
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 1.0
 * @since 29.09.2022
 */

class MainApplication : Application() {
    private val database: ParliamentDatabase by lazy { ParliamentDatabase.getDatabase(this) }

    val membersRepository: MembersRepository by lazy{
        MembersRepository(
            membersBasicDataDao = database.membersBasicDataDao,
            membersExtraDataDao = database.membersExtraDataDao,
            apiClient = ParliamentApiClient.retrofitService
        )
    }

    val commentsRepository: CommentsRepository by lazy {
        CommentsRepository(database.commentsDao)
    }


}