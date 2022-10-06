package fi.shaynek.parliamentfinland.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import kotlinx.coroutines.flow.Flow

@Dao
interface MembersExtraDataDao {
    @Query("SELECT * FROM member_extra_data WHERE heteka_id = :hetekaId")
    fun getMemberExtraData(hetekaId:Int):Flow<List<MembersExtraData>>

    //Methods that retuns livedaa or Flow
    @Query("SELECT * FROM member_extra_data")
    fun getMembersExtraData():Flow<List<MembersExtraData>>

    @Insert(onConflict = REPLACE)
    suspend fun addMemberExtraData(membersExtraData: MembersExtraData)

    @Update
    suspend fun updateMemberExtraData(membersExtraData: MembersExtraData)

     @Delete
    suspend fun deleteMemberExtraData(membersExtraData: MembersExtraData)

}