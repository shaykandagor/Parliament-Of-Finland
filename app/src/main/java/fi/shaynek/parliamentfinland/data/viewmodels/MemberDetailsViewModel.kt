package fi.shaynek.parliamentfinland.data.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.*
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.network.ParliamentApiStatus
import fi.shaynek.parliamentfinland.data.repositories.MembersRepository
import fi.shaynek.parliamentfinland.utils.Shared
import kotlinx.coroutines.launch

/**
 * This class is designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows members basic and extra data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *  @author Shayne Kandagor
 * @studentId 2112916
 * @Version 1.0
 * @since 04.09.2022
 */

class MemberDetailsViewModel(
    /**
     * The data source this ViewModel will fetch results from.
     */
    private val membersRepository: MembersRepository,
) :

    ViewModel() {

    fun observeStatus(lifeCycleOwner: LifecycleOwner) {
        membersRepository.fectStatus.observe(lifeCycleOwner, Observer {
            Log.d("Ous:fectStatus", it.toString())
        })
        membersRepository.extraDataStatus.observe(lifeCycleOwner, Observer {
            Log.d("Ous:ex", it.toString())
        })
        membersRepository.basicDataStatus.observe(lifeCycleOwner, Observer {
            Log.d("Ous:bsStatus", it.toString())
        })
    }

    val basicDataNet = MutableLiveData<List<MembersBasicData>>()
    val extraDataNet = MutableLiveData<List<MembersExtraData>>()
    private var isFiltered = false

    val img = MutableLiveData<Bitmap?>()

    private val _basicData: LiveData<List<MembersBasicData>> = membersRepository.basicData
    private val _extraData: LiveData<List<MembersExtraData>> = membersRepository.extraData

    val basicData: LiveData<List<MembersBasicData>>
        get() = _basicData
    val extraData: LiveData<List<MembersExtraData>>
        get() = _extraData

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

    private fun fetchBasicData() {
        viewModelScope.launch {
            try {
                val listResult = membersRepository.fetchBasicData()
                //TODO COME HERE
                Log.d("Ous:bsCoun", listResult.size.toString())
                Log.d("Ous:bs917", listResult.filter {
                    return@filter it.hetekaId == 917
                }.toString())
                basicDataNet.value = listResult
            } catch (e: Exception) {
                Log.d("Ous:bs", e.toString())
//                data.value = listOf()
            }


        }
    }

    private fun fetchExtraData() {
        viewModelScope.launch {
            try {
                val listResult = membersRepository.fetchExtraData()
                //TODO COME HERE
                Log.d("Ous:exCoun", listResult.size.toString())
                Log.d("Ous:ex917", listResult.filter {
                    return@filter it.hetekaId == 917
                }.toString())
                extraDataNet.value = listResult
            } catch (e: Exception) {
                Log.d("Ous:ex", e.toString())
            }
        }
    }

    fun syncFetch(lifeCycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            fetchBasicData()

            membersRepository.basicDataStatus.observe(lifeCycleOwner, Observer {
                if (it == ParliamentApiStatus.DONE) {
                    fetchExtraData()
                } else if (it == ParliamentApiStatus.LOADING) {
                    membersRepository.fectStatus.value = ParliamentApiStatus.LOADING
                    membersRepository.extraDataStatus.value = ParliamentApiStatus.LOADING
                } else {
                    membersRepository.fectStatus.value = ParliamentApiStatus.ERROR
                    membersRepository.extraDataStatus.value = ParliamentApiStatus.ERROR
                }
            })
            membersRepository.extraDataStatus.observe(lifeCycleOwner, Observer {
                if (it == ParliamentApiStatus.DONE) {
                    membersRepository.fectStatus.value = ParliamentApiStatus.DONE
                } else if (it == ParliamentApiStatus.LOADING) {
                    membersRepository.fectStatus.value = ParliamentApiStatus.LOADING
                } else {
                    membersRepository.fectStatus.value = ParliamentApiStatus.ERROR
                }
            })
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
}

class MemberDetailsViewModelFactory(
    private val membersRepository: MembersRepository
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberDetailsViewModel::class.java)) {
            return MemberDetailsViewModel(
                membersRepository
            ) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }

}