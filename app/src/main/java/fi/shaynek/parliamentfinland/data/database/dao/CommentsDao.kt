package fi.shaynek.parliamentfinland.data.database.dao
import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fi.shaynek.parliamentfinland.data.database.entity.Comments

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments WHERE heteka_id = :hetekaId")
    fun getMemberComments(hetekaId: Int): Flow<List<Comments>>

    @Query("SELECT * FROM comments")
    fun getComments(): Flow<List<Comments>>


    @Insert
    suspend fun addComment(comments: Comments)

    @Delete
    suspend fun deleteComment(comments: Comments)

    @Update
    suspend fun updateComment(comments: Comments)
}