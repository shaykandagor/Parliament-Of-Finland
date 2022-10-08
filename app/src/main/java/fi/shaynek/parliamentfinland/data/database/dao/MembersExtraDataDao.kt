package fi.shaynek.parliamentfinland.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import kotlinx.coroutines.flow.Flow

/**
 * This classes uses SQLite commands to access and fetch extra members of parliament details from prepopulate database
 * @author Shayne Kandagor
 * @Version 1.0
 * @since 29.09.2022
 */


@Dao
interface MembersExtraDataDao {
    /**
     * Query to select all extra details about parliament members with the same hetekaId
     * @param hetekaId
     * @return extra details like year of birth, constituency and twitter handles of some of the members
     */


    @Query("SELECT * FROM member_extra_data WHERE heteka_id = :hetekaId")
    fun getMemberExtraData(hetekaId:Int):Flow<List<MembersExtraData>>


    //Methods that retuns livedaa or Flow
    @Query("SELECT * FROM member_extra_data")
    fun getMembersExtraData():Flow<List<MembersExtraData>>

    /**
     * Query to add extra  member of parliament data in the database
     */

    @Insert(onConflict = REPLACE)
    suspend fun addMemberExtraData(membersExtraData: MembersExtraData)

    /**
     * Query to update member of parliament data in the database
     */

    @Update
    suspend fun updateMemberExtraData(membersExtraData: MembersExtraData)

    /**
     * Query to delete member of parliament data in the database
     */

     @Delete
    suspend fun deleteMemberExtraData(membersExtraData: MembersExtraData)

}