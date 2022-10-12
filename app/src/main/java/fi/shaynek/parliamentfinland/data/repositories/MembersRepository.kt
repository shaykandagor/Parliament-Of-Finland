package fi.shaynek.parliamentfinland.data.repositories

import androidx.lifecycle.*
import fi.shaynek.parliamentfinland.data.database.dao.MemberBasicDataDao
import fi.shaynek.parliamentfinland.data.database.dao.MembersExtraDataDao
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.network.ParliamentApiStatus
import fi.shaynek.parliamentfinland.data.network.ParliamentService
import kotlinx.coroutines.*


/**
 * Repository for fetching members basic and extra data from the network and storing them on disk
 *  @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 04.09.2022
 */

class MembersRepository(
    private val membersBasicDataDao: MemberBasicDataDao,
    private val membersExtraDataDao: MembersExtraDataDao,
    private val apiClient: ParliamentService
) {
    val basicDataStatus = MutableLiveData<ParliamentApiStatus>()
    val extraDataStatus = MutableLiveData<ParliamentApiStatus>()

    /**
     * @param fetchStatus indicates when both basic and extra data
     * have been fetched from the network and saved in the database successfully
     * That is both basic and extra data status equals to DONE
     */
    val fetchStatus = MutableLiveData<ParliamentApiStatus>(ParliamentApiStatus.LOADING)
    private val _basicData = membersBasicDataDao.getMembersBasicData().asLiveData()
    private val _extraData = membersExtraDataDao.getMembersExtraData().asLiveData()


    val basicData: LiveData<List<MembersBasicData>>
        get() = _basicData
    val extraData: LiveData<List<MembersExtraData>>
        get() = _extraData

    /**
     * It fetches parliament member basic data from the web RESTFUL server and
     * returns a list of basic data objects using ParliamentApi service
     * basicDataStatus indicates the current status of basic data fetch operation
     * ie DONE when loaded successfully from the network
     * LOADING when the basic data is still loading
     * ERROR when a network error occurs
     */

    suspend fun fetchBasicData(){
        basicDataStatus.value = ParliamentApiStatus.LOADING
        val membersBasicData = apiClient.getBasicData()
        membersBasicData.forEach{
            try {
                addBasicData(it)
            } catch (e: Exception) {
                basicDataStatus.value = ParliamentApiStatus.ERROR
            }

        }
        basicDataStatus.value = ParliamentApiStatus.DONE

    }

    /**
     * Similar to fetchBasicData except that its done on extra data
     */

    private suspend fun fetchExtraData() {
        extraDataStatus.value = ParliamentApiStatus.LOADING
        val membersExtraData = apiClient.getExtraData()
        membersExtraData.forEach {
            try {
                addExtraData(it)
            } catch (e: Exception) {
                extraDataStatus.value = ParliamentApiStatus.ERROR

            }
        }
        extraDataStatus.value = ParliamentApiStatus.DONE
    }

    suspend fun addBasicData(membersBasicData: MembersBasicData) {
        membersBasicDataDao.addMemberBasicData(membersBasicData)
    }

    suspend fun addExtraData(membersExtraData: MembersExtraData) {
        membersExtraDataDao.addMemberExtraData(membersExtraData)
    }

    suspend fun updateBasicData(membersBasicData: MembersBasicData) {
        membersBasicDataDao.updateMemberBasicData(membersBasicData)
    }

    suspend fun updateExtraData(membersExtraData: MembersExtraData) {
        membersExtraDataDao.updateMemberExtraData(membersExtraData)
    }
    /**
     * This synchronizes fetching of basic and extra data from the network
     * and storing them in the database using basicDataStatus and extraDataStatus
     * This ensures that basic data is fetched and stored in the database first
     * and saved  before fetching and storing extra data in the database
     * This is essential to avoid SQLConstraint Exception which may arise when storing
     * extra data with no related basic data in the parent table due to parent-child relationship
     * between basic data(parent) and extra data(child) entity
     */

    fun syncFetch(lifecycleOwner: LifecycleOwner,scope: CoroutineScope
    ) {
        scope.launch {
            fetchBasicData()

            basicDataStatus.observe(lifecycleOwner, Observer {
                if (it == ParliamentApiStatus.DONE) {
                    scope.launch {
                        fetchExtraData()
                    }
                } else if (it == ParliamentApiStatus.LOADING) {
                    fetchStatus.value = ParliamentApiStatus.LOADING
                    extraDataStatus.value = ParliamentApiStatus.LOADING
                } else {
                    fetchStatus.value = ParliamentApiStatus.ERROR
                    extraDataStatus.value = ParliamentApiStatus.ERROR
                }
            })
            extraDataStatus.observe(lifecycleOwner, Observer {
                if (it == ParliamentApiStatus.DONE) {
                    fetchStatus.value = ParliamentApiStatus.DONE
                } else if (it == ParliamentApiStatus.LOADING) {
                    fetchStatus.value = ParliamentApiStatus.LOADING
                } else {
                    fetchStatus.value = ParliamentApiStatus.ERROR
                }
            })
           }
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {

        }
    }
}