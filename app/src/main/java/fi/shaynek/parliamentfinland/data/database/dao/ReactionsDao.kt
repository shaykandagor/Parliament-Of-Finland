package fi.shaynek.parliamentfinland.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.Reactions
import kotlinx.coroutines.flow.Flow

/**
 *This classes uses SQLite commands to access and fetch reactions from users
 * about members of parliament data from the network
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 4.0
 * @since 26.09.2022
 */

@Dao
interface ReactionsDao {
    @Query("SELECT * FROM reaction")
    fun getReaction():Flow<List<Reactions>>

    @Update
    suspend fun updateReaction(reactions: Reactions)

    @Insert(onConflict = REPLACE)
    suspend fun addReaction(reactions: Reactions)
}