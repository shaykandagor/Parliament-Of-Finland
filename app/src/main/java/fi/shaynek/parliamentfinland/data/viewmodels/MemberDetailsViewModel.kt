package fi.shaynek.parliamentfinland.data.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.*
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.repositories.MembersRepository
import fi.shaynek.parliamentfinland.data.network.ParliamentService
import fi.shaynek.parliamentfinland.utils.Shared
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MemberDetailsViewModel(
    private val membersRepository: MembersRepository,
    private val apiClient: ParliamentService
) :
    ViewModel() {
    val basicDataNet = MutableLiveData<List<MembersBasicData>>()
    val extraDataNet = MutableLiveData<List<MembersExtraData>>()
    val greet = MutableLiveData<Int>()
    val img = MutableLiveData<Bitmap?>()

    init {
        greet.value = 0
    }

    fun inC() {
        greet.value = (greet.value)?.plus(1)
    }

    val basicData: LiveData<List<MembersBasicData>> = membersRepository.getMembersBasicData()
    val extraData: LiveData<List<MembersExtraData>> = membersRepository.getMembersExtraData()

    private fun addBasicData(basicData: MembersBasicData) {
        viewModelScope.launch {
            membersRepository.addBasicData(basicData)
        }
    }

    private fun addExtraData(extraData: MembersExtraData) {
        viewModelScope.launch {
            membersRepository.addExtraData(extraData)
        }
    }

    fun fetchBasicData() {
        viewModelScope.launch {
            try {
                val listResult = apiClient.getBasicData()
                //TODO COME HERE
                Log.d("Ous", listResult.toString())
                basicDataNet.value = listResult
            } catch (e: Exception) {
                Log.d("Ous:bs", e.toString())
//                data.value = listOf()
            }


        }
    }

    fun fetchExtraData() {
        viewModelScope.launch {
            try {
                val listResult = apiClient.getExtraData()
                //TODO COME HERE
                Log.d("Ous", listResult.toString())
                extraDataNet.value = listResult
            } catch (e: Exception) {
                Log.d("Ous:ex", e.toString())
            }
        }
    }

    private fun getBasicData(
        hetekaId: Int,
        seatNo: Int,
        minister: Boolean,
        photoUrl: String,
        party: String,
        lastName: String,
        firstName: String
    ): MembersBasicData {
        return MembersBasicData(
            hetekaId = hetekaId,
            seatNo = seatNo,
            minister = minister,
            photoUrl = photoUrl,
            party = party,
            lastName = lastName,
            firstName = firstName
        )
    }

    private fun getExtraData(
        hetekaId: Int,
        twitter: String,
        bornYear: Int,
        constituency: String
    ): MembersExtraData {
        return MembersExtraData(
            hetekaId = hetekaId,
            twitterHandle = twitter,
            bornYear = bornYear,
            constituency = constituency
        )
    }

    fun addExtraData(
        hetekaId: Int,
        twitter: String,
        bornYear: Int,
        constituency: String
    ) {
        val data = getExtraData(
            hetekaId = hetekaId,
            twitter = twitter,
            bornYear = bornYear,
            constituency = constituency
        )
        addExtraData(data)
    }

    fun addBasicData(
        hetekaId: Int,
        seatNo: Int,
        minister: Boolean,
        photoUrl: String,
        party: String,
        lastName: String,
        firstName: String
    ) {
        val data: MembersBasicData = getBasicData(
            hetekaId = hetekaId,
            seatNo = seatNo,
            minister = minister,
            photoUrl = photoUrl,
            party = party,
            lastName = lastName,
            firstName = firstName
        )
        addBasicData(data)
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
        val conn: HttpURLConnection = photoUrl.openConnection() as HttpURLConnection
        conn.doInput = true
        conn.connect()
        val inputStream: InputStream = conn.inputStream
        val btMap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
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

    fun loadImage(url: String){
        viewModelScope.launch {
            try {
                val img_url = "${Shared.IMG_BASE_URL}$url"
                Log.d("Ous:Success load: ", img_url)
                img.value = getBitMap(img_url)
            }catch (e:Exception){
                Log.d("Ous:bmap", e.toString())
            }

        }
    }

}

class MemberDetailsViewModelFactory(
    private val membersRepository: MembersRepository,
    private val apiClient: ParliamentService
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberDetailsViewModel::class.java)) {
            return MemberDetailsViewModel(
                membersRepository,
                apiClient
            ) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }

}