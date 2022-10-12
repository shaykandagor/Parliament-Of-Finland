package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.adapter.CommentsRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.models.Author
import fi.shaynek.parliamentfinland.data.models.CommentItem
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentCommentsBinding

/**
 *This displays  the comments of users about members of Parliament
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 06.10.2022
 */


class CommentsFragment : Fragment() {
    lateinit var viewModel: CommentsViewModel
    lateinit var binding: FragmentCommentsBinding
    private var hetekaId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCommentsBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            CommentsViewModelFactory(
                (requireActivity().application as MainApplication)
                    .commentsRepository
            )
        )[CommentsViewModel::class.java]
        hetekaId = arguments?.getString("hetekaId")?.toIntOrNull()
        addObserver()
        addEventListeners()
        registerForContextMenu(binding.comentsRecyclerView)
        return binding.root
    }

    private fun addEventListeners() {
        hetekaId?.let {_id ->
            binding.postComment.setOnClickListener {
                if (viewModel.isInputValid(binding.commentText.text.toString())) {
                    viewModel.addComment(hetekaId = _id, binding.commentText.text.toString(), Author)
                    binding.commentText.setText("")
                }
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.comment_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.comment_edit -> {}
            R.id.comment_delete -> {}
            else -> super.onContextItemSelected(item)
        }
        return true
    }

    private fun addObserver() {
        viewModel.comments.observe(requireActivity(), Observer { comments ->
            if (hetekaId != null) {
                val memberComments = comments.filter { comment ->
                    return@filter comment.hetekaId == hetekaId
                }
                binding.comentsRecyclerView.adapter = CommentsRecyclerAdapter(
                    memberComments.map {
                        return@map CommentItem(
                            author = Author,
                            content = it.comment,
                            created = it.created
                        )
                    }
                )
            }
        })
    }
}