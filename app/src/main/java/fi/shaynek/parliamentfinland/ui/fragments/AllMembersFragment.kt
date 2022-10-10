package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fi.shaynek.parliamentfinland.adapter.AllMembersRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.CommentsViewModelFactory
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentAllMembersBinding

/**
 * It defines the comments about all members in a list
 * It also defines the reactions of users about members counts the number likes and dislikes
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
        // Inflate the layout for this fragment
        binding = FragmentAllMembersBinding.inflate(inflater)
//        var v = inflater.inflate(R.layout.fragment_all_members_fragment, container, false)
//        val memberCardLargeRecyclerView:RecyclerView = v.findViewById(R.id.member_card_large_recycler_view)

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
//        memberCardLargeRecyclerView.adapter = AllMembersRecyclerAdapter(
        binding.memberCardLargeRecyclerView.adapter = AllMembersRecyclerAdapter(
            requireActivity(),
            viewModel.basicData,
            viewModel.extraData,
            commentsViewModel.comments,
//            commentsViewModel.reactions,
//            commentsViewModel::addReaction,
        )
        return binding.root
    }
}