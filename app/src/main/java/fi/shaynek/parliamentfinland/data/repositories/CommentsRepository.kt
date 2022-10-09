package fi.shaynek.parliamentfinland.data.repositories

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import fi.shaynek.parliamentfinland.data.database.dao.CommentsDao
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import kotlinx.coroutines.flow.Flow

/**
 * Repository for adding and updating comments of users  and storing them on disk
 *  @author Shayne Kandagor
 * @studentId 2112916
 * @Version 1.0
 * @since 04.09.2022
 */

class CommentsRepository(private val commentsDao: CommentsDao) {

    private val _comments: LiveData<List<Comments>> = commentsDao.getComments().asLiveData()
    val comments: LiveData<List<Comments>>
        get() = _comments


    suspend fun addComment(comment: Comments){
        try {
            commentsDao.addComment(comment)
        }catch (e: SQLiteConstraintException){
//            If no related data in the parent table then ignore

        }catch (e:Exception){

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