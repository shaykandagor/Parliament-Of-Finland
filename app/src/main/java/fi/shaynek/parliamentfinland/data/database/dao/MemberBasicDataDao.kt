package fi.shaynek.parliamentfinland.data.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import kotlinx.coroutines.flow.Flow

/**
 *This classes uses SQLite commands to access and fetch members of parliament data from the network
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 26.09.2022
 */

@Dao
interface MemberBasicDataDao {

    /**
     * Query to select all members from members database
     * @return basic data from member database about members of parliament
     */

    @Query("SELECT * FROM members_basic_data WHERE heteka_id=:hetekaId")
    fun getMemberBasicData(hetekaId: Int): Flow<List<MembersBasicData>>

    @Query("SELECT * FROM members_basic_data")
    fun getMembersBasicData(): Flow<List<MembersBasicData>>

    /**
     * Query to add member of parliament in the database
     */

    @Insert
    suspend fun addMemberBasicData(membersBasicData: MembersBasicData)

    /**
     * Query to update member of parliament data in the database
     */

    @Update
    suspend fun updateMemberBasicData(membersBasicData: MembersBasicData)

    /**
     * Query to delete member of parliament in the database
     */

    @Delete
    suspend fun deleteMemberBasicData(membersBasicData: MembersBasicData)
}