package fi.shaynek.parliamentfinland.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import fi.shaynek.parliamentfinland.data.database.dao.MemberBasicDataDao
import fi.shaynek.parliamentfinland.data.database.dao.MembersExtraDataDao
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.network.ParliamentApiStatus
import fi.shaynek.parliamentfinland.data.network.ParliamentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * Repository for fetching members basic and extra data from the network and storing them on disk
 *  @author Shayne Kandagor
 * @studentId 2112916
 * @Version 1.0
 * @since 04.09.2022
 */

class MembersRepository(
    private val membersBasicDataDao: MemberBasicDataDao,
    private val membersExtraDataDao: MembersExtraDataDao,
    private val apiClient: ParliamentService
) {
    val basicDataStatus = MutableLiveData<ParliamentApiStatus>()
    val extraDataStatus = MutableLiveData<ParliamentApiStatus>()
    val fectStatus = MutableLiveData<ParliamentApiStatus>(ParliamentApiStatus.LOADING)
    private val _basicData = membersBasicDataDao.getMembersBasicData().asLiveData()
    private val _extraData = membersExtraDataDao.getMembersExtraData().asLiveData()


    val basicData: LiveData<List<MembersBasicData>>
        get() = _basicData
    val extraData: LiveData<List<MembersExtraData>>
        get() = _extraData

    //TODO DELETE ME
    private lateinit var listResult: List<MembersBasicData>

    suspend fun fetchBasicData(): List<MembersBasicData> {
        basicDataStatus.value = ParliamentApiStatus.LOADING
        val data = apiClient.getBasicData()
        listResult = data
        for (bsd in data) {
            try {
                addBasicData(bsd)
            } catch (e: Exception) {
                basicDataStatus.value = ParliamentApiStatus.ERROR
            }

        }
        basicDataStatus.value = ParliamentApiStatus.DONE
        return data
    }

    suspend fun fetchExtraData(): List<MembersExtraData> {
        extraDataStatus.value = ParliamentApiStatus.LOADING
        val data = apiClient.getExtraData()
        for (ex in data) {
            try {
                addExtraData(ex)
            } catch (e: Exception) {
                extraDataStatus.value = ParliamentApiStatus.ERROR

            }

        }
        extraDataStatus.value = ParliamentApiStatus.DONE
        return data
    }

    fun getBasicData(hetekaId: Int): LiveData<List<MembersBasicData>> {
        return membersBasicDataDao.getMemberBasicData(hetekaId).asLiveData()
    }

    fun getExtraData(hetekaId: Int): LiveData<List<MembersExtraData>> {
        return membersExtraDataDao.getMemberExtraData(hetekaId).asLiveData()
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
}