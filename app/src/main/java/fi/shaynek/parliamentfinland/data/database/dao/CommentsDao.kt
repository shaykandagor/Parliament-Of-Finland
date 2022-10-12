package fi.shaynek.parliamentfinland.data.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import kotlinx.coroutines.flow.Flow

/**
 * This class uses SQLite commands to access, fetch and insert/delete comments of users about members of parliament from database
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 3.0
 * @since 26.09.2022
 */

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments WHERE heteka_id = :hetekaId")
    fun getMemberComments(hetekaId: Int): Flow<List<Comments>>

    @Query("SELECT * FROM comments")
    fun getComments(): Flow<List<Comments>>


    @Insert(onConflict = REPLACE)
    suspend fun addComment(comments: Comments)

    @Delete
    suspend fun deleteComment(comments: Comments)

    @Update
    suspend fun updateComment(comments: Comments)
}