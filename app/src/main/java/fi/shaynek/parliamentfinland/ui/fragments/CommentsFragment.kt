package dev.vstec.parliament2.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.vstec.parliament2.R
import dev.vstec.parliament2.adapters.CommentsRecyclerAdapter
import dev.vstec.parliament2.app.MainApplication
import dev.vstec.parliament2.data.models.CommentItem
import dev.vstec.parliament2.data.viewmodels.CommentsViewModel
import dev.vstec.parliament2.data.viewmodels.CommentsViewModelFactory
import dev.vstec.parliament2.databinding.FragmentCommentsBinding


class CommentsFragment : Fragment() {
    lateinit var viewModel: CommentsViewModel
    lateinit var binding: FragmentCommentsBinding
    private val hetekaId:Int = 802
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommentsBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            requireActivity(),
            CommentsViewModelFactory(
                (requireActivity().application as MainApplication)
                    .commentsRepository
            )
        )[CommentsViewModel::class.java]
        addObserver()
        addEventListeners()
        registerForContextMenu(binding.comentsRecyclerView)
        return binding.root
    }

    private fun addEventListeners() {
        binding.postComment.setOnClickListener {
            if (viewModel.isInputValid(binding.commentText.text.toString())) {
                viewModel.addComment(hetekaId = hetekaId, binding.commentText.text.toString())
                binding.commentText.setText("")
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
        when(item.itemId){
            R.id.comment_edit -> {}
            R.id.comment_delete -> {}
            else->super.onContextItemSelected(item)
        }
        return true
    }

    private fun addObserver() {
        viewModel.comments.observe(requireActivity(), Observer { comments ->
            val memberComments = comments.filter { comment ->
                return@filter comment.hetekaId == hetekaId
            }
            binding.comentsRecyclerView.adapter = CommentsRecyclerAdapter(
                memberComments.map {
                    return@map CommentItem(
                        author = "Some Author",
                        content = it.comment
                    )
                }
            )
        })
    }


}