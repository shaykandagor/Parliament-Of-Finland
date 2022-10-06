package fi.shaynek.parliamentfinland.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import fi.shaynek.parliamentfinland.data.database.dao.MemberBasicDataDao
import fi.shaynek.parliamentfinland.data.database.dao.MembersExtraDataDao
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.network.ParliamentService

class MembersRepository(
    private val membersBasicDataDao: MemberBasicDataDao,
    private val membersExtraDataDao: MembersExtraDataDao,
    private val apiClient: ParliamentService
) {
    suspend fun fetchBasicData(): List<MembersBasicData> {
        val basicData = apiClient.getBasicData()
        return basicData
    }

    suspend fun fetchExtraData(): List<MembersExtraData> {
        val extraData = apiClient.getExtraData()
        return extraData
    }

    fun getBasicData(hetekaId: Int): LiveData<List<MembersBasicData>> {
        return membersBasicDataDao.getMemberBasicData(hetekaId).asLiveData()
    }

    fun getExtraData(hetekaId: Int): LiveData<List<MembersExtraData>> {
        return membersExtraDataDao.getMemberExtraData(hetekaId).asLiveData()
    }

    fun getMembersBasicData(): LiveData<List<MembersBasicData>> {
        return membersBasicDataDao.getMembersBasicData().asLiveData()
    }

    fun getMembersExtraData(): LiveData<List<MembersExtraData>> {
        return membersExtraDataDao.getMembersExtraData().asLiveData()
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