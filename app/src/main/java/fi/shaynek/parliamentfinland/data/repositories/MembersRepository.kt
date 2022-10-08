package fi.shaynek.parliamentfinland.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import dev.vstec.parliament2.data.dao.MemberBasicDataDao
import dev.vstec.parliament2.data.dao.MembersExtraDataDao
import dev.vstec.parliament2.data.entity.MembersBasicData
import dev.vstec.parliament2.data.entity.MembersExtraData
import dev.vstec.parliament2.services.ParliamentApiStatus
import dev.vstec.parliament2.services.ParliamentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

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
                //Log.d("Ous", "added: $bsd")
            } catch (e: Exception) {
                basicDataStatus.value = ParliamentApiStatus.ERROR
                Log.d("Ous:ERROR$bsd", e.toString())
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
                //Log.d("Ous", "added: $ex")
            } catch (e: Exception) {
                extraDataStatus.value = ParliamentApiStatus.ERROR
                Log.d("Ous:ERROR$ex", e.toString())
                Log.d("Ous:bse${ex.hetekaId}", listResult.filter {
                    return@filter it.hetekaId == ex.hetekaId
                }.toString())
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

    suspend fun getBitMap(url: String): Bitmap {
        /**
         * Throws 2 errors
         */
        /*
        lateinit var photoUrl: URL
        lateinit var btMap: Bitmap
        */
        val photoUrl = URL(url)
        val conn: HttpURLConnection =
            withContext(Dispatchers.IO) {
                photoUrl.openConnection()
            } as HttpURLConnection
        conn.doInput = true
        withContext(Dispatchers.IO) {
            conn.connect()
        }
        val inputStream: InputStream = conn.inputStream
        val btMap = BitmapFactory.decodeStream(inputStream)
        withContext(Dispatchers.IO) {
            inputStream.close()
        }
        /*try {
            photoUrl = URL(url)

        } catch (e: MalformedURLException) {
            Log.d("Ous", e.toString())
        }
        try {
            val conn: HttpURLConnection = photoUrl.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val inputStream: InputStream = conn.inputStream
            btMap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

        } catch (e: Exception) {
            Log.d("Ous", e.toString())
        }*/
        return btMap
    }


}