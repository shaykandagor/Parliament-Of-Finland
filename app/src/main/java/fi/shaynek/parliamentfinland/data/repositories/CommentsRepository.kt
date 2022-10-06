package fi.shaynek.parliamentfinland.data.repositories

import fi.shaynek.parliamentfinland.data.database.dao.CommentsDao
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import kotlinx.coroutines.flow.Flow

class CommentsRepository(private val commentsDao: CommentsDao) {
    suspend fun addComment(comment: Comments){
        commentsDao.addComment(comment)
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
}