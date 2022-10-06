package fi.shaynek.parliamentfinland.data.viewmodels

import androidx.lifecycle.*
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import fi.shaynek.parliamentfinland.data.repositories.CommentsRepository
import kotlinx.coroutines.launch

class CommentsViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {
    val comments: LiveData<List<Comments>> = commentsRepository.getComments().asLiveData()
    fun memberComments(hetekaId: Int): LiveData<List<Comments>> {
        return commentsRepository.getMemberComments(hetekaId).asLiveData()
    }

    private fun addComment(comment: Comments) {
        viewModelScope.launch {
            commentsRepository.addComment(comment)
        }
    }

    private fun getComment(hetekaId: Int, comment: String): Comments {
        return Comments(
            hetekaId = hetekaId,
            comment = comment
        )
    }

    fun addComment(hetekaId: Int, comment: String) {
        val newComment: Comments = getComment(hetekaId, comment)
        addComment(newComment)
    }

    fun updateComment(comment: Comments) {
        viewModelScope.launch {
            commentsRepository.updateComment(comment)
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