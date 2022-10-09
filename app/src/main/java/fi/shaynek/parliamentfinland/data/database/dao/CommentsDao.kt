package fi.shaynek.parliamentfinland.data.database.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.Comments

/**
 * This class uses SQLite commands to access, fetch and insert/delete comments of users about members of parliament from database
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 26.09.2022
 */

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments WHERE heteka_id = :hetekaId")
    fun getMemberComments(hetekaId: Int): Flow<List<Comments>>

    /**
     * Query to return all comment
     * @return Lists all comments available in the comments Fragment
     */

    @Query("SELECT * FROM comments")
    fun getComments(): Flow<List<Comments>>

    /**
     * Insert comment function
     */


    @Insert
    suspend fun addComment(comments: Comments)

    /**
     * Delete comment function
     */


    @Delete
    suspend fun deleteComment(comments: Comments)

    /**
     * Query to update current comment
     */

    @Update
    suspend fun updateComment(comments: Comments)
}