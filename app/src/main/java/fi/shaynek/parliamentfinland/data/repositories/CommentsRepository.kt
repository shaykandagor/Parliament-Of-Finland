package fi.shaynek.parliamentfinland.data.repositories

import android.database.sqlite.SQLiteConstraintException
import fi.shaynek.parliamentfinland.data.database.dao.CommentsDao
import fi.shaynek.parliamentfinland.data.database.dao.ReactionsDao
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import fi.shaynek.parliamentfinland.data.database.entity.Reactions
import kotlinx.coroutines.flow.Flow

/**
 * Repository for adding and updating comments and reactions of users  and storing them on disk
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 1.0
 * @since 04.09.2022
 */

class CommentsRepository(
    private val commentsDao: CommentsDao,
    private val reactionsDao: ReactionsDao
) {

    suspend fun addComment(comment: Comments){
        try {
            commentsDao.addComment(comment)
        }catch (e: SQLiteConstraintException){

        }

    }
    suspend fun updateComment(comment: Comments){
        commentsDao.updateComment(comment)
    }
    fun getComments(): Flow<List<Comments>>{
        return commentsDao.getComments()
    }
    fun getMemberComments(hetekaId:Int):Flow<List<Comments>> {
        return commentsDao.getMemberComments(hetekaId)
    }

    fun getReactions(): Flow<List<Reactions>> {
        return reactionsDao.getReaction()
    }
    suspend fun updateReaction(reactions: Reactions){
        reactionsDao.updateReaction(reactions)
    }
    suspend fun addReaction(reactions: Reactions){
        reactionsDao.addReaction(reactions)
    }
}