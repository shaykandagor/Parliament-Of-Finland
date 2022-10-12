package fi.shaynek.parliamentfinland.data.viewmodels

import androidx.lifecycle.*
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import fi.shaynek.parliamentfinland.data.database.entity.Reactions
import fi.shaynek.parliamentfinland.data.repositories.CommentsRepository
import kotlinx.coroutines.launch

/**
 * This class is designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows comments and reactions about a particular member to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 3.0
 * @since 04.09.2022
 */

class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {
    val comments: LiveData<List<Comments>> = commentsRepository.getComments().asLiveData()
    val reactions:LiveData<List<Reactions>> = commentsRepository.getReactions().asLiveData()
    fun memberComments(hetekaId: Int): LiveData<List<Comments>> {
        return commentsRepository.getMemberComments(hetekaId).asLiveData()
    }

    private fun addComment(comment: Comments) {
        viewModelScope.launch {
            commentsRepository.addComment(comment)
        }
    }

    private fun getComment(hetekaId: Int, comment: String, author: String): Comments {
        return Comments(
            hetekaId = hetekaId,
            comment = comment,
            author = author
        )
    }

    fun addComment(hetekaId: Int, comment: String, author: String) {
        val newComment: Comments = getComment(hetekaId, comment, author)
        addComment(newComment)
    }

    fun isInputValid(comment: String):Boolean{
        return comment.isNotBlank() && comment.isNotEmpty()
    }

    fun updateComment(comment: Comments) {
        viewModelScope.launch {
            commentsRepository.updateComment(comment)
        }
    }

    fun updateReaction(reactions: Reactions){
        viewModelScope.launch {
            commentsRepository.updateReaction(reactions)
        }
    }
    fun addReaction(reactions: Reactions){
        viewModelScope.launch {
            commentsRepository.addReaction(reactions)
        }
    }


}

class CommentsViewModelFactory(private val commentsRepository: CommentsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentsViewModel(commentsRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown view Model Class")
    }
}