package fi.shaynek.parliamentfinland.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberBasicDataDao {
    @Query("SELECT * FROM members_basic_data WHERE heteka_id=:hekaId")
    fun getMemberBasicData(hekaId: Int): Flow<List<MembersBasicData>>

    @Query("SELECT * FROM members_basic_data")
    fun getMembersBasicData(): Flow<List<MembersBasicData>>

    @Insert
    suspend fun addMemberBasicData(membersBasicData: MembersBasicData)

    @Update
    suspend fun updateMemberBasicData(membersBasicData: MembersBasicData)

    @Delete
    suspend fun deleteMemberBasicData(membersBasicData: MembersBasicData)
}