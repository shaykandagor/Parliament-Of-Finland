package fi.shaynek.parliamentfinland.data.repositories

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import fi.shaynek.parliamentfinland.data.database.dao.CommentsDao
import fi.shaynek.parliamentfinland.data.database.entity.Comments

import kotlinx.coroutines.flow.Flow

class CommentsRepository(private val commentsDao: CommentsDao) {
    /*
    private val _comments: LiveData<List<Comments>> = commentsDao.getComments().asLiveData()
    val comments: LiveData<List<Comments>>
        get() = _comments
     */

    suspend fun addComment(comment: Comments){
        try {
            commentsDao.addComment(comment)
        }catch (e: SQLiteConstraintException){
            Log.d("Ous", e.toString())
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
}