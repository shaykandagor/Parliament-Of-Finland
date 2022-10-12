package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModelFactory
import fi.shaynek.parliamentfinland.adapter.AllMembersRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentAllMembersBinding

/**
 *This displays all the members of parliament with thier basic and extra details
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 06.10.2022
 */



class AllMembersFragment : Fragment() {
    lateinit var binding: FragmentAllMembersBinding
    private lateinit var viewModel: MemberDetailsViewModel
    private lateinit var commentsViewModel: CommentsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllMembersBinding.inflate(inflater)

        viewModel = ViewModelProvider(
            requireActivity(), MemberDetailsViewModelFactory(
                (requireActivity().application as MainApplication).membersRepository
            )
        )[MemberDetailsViewModel::class.java]
        commentsViewModel = ViewModelProvider(
            requireActivity(),
            CommentsViewModelFactory(
                (requireActivity().application as MainApplication).commentsRepository
            )
        )[CommentsViewModel::class.java]
        binding.memberCardLargeRecyclerView.adapter = AllMembersRecyclerAdapter(
            requireActivity(),
            viewModel.basicData,
            viewModel.extraData,
            commentsViewModel.comments,
            commentsViewModel.reactions,
            commentsViewModel::addReaction,
        )
        return binding.root
    }
}