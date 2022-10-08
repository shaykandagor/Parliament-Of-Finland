package fi.shaynek.parliamentfinland.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fi.shaynek.parliamentfinland.MainActivity
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.adapter.PartyDetailedRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.models.Parliament
import fi.shaynek.parliamentfinland.data.models.PartyMemberItem
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentPartyDetailedBinding

class PartyDetailedFragment : Fragment() {
    private lateinit var viewModel: MemberDetailsViewModel
    lateinit var binding: FragmentPartyDetailedBinding
    lateinit var navController: NavController
    private var currParty: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPartyDetailedBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            requireActivity(), MemberDetailsViewModelFactory(
                (requireActivity().application as MainApplication).membersRepository
            )
        )[MemberDetailsViewModel::class.java]
        navController = findNavController()
        currParty = arguments?.getString("party")

        addObservers()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun addObservers() {
        viewModel.basicData.observe(requireActivity(), Observer { members ->
            val parliament = Parliament(members)

            if (currParty != null) {
                val partyMembers = parliament.partyMembers(currParty as String)
                binding.partyDetailedIcon.setImageResource(R.drawable.ic_account)
                binding.partyDetailedLeader.text =
                    "Leader: " + partyMembers.first().firstName + " " + partyMembers.first().lastName
                binding.partyDetailedTitle.text = "Party: $currParty"
                binding.partyDetailedMembersCount.text =
                    "Total Members" + partyMembers.size.toString()
                binding.partyDetailedRecyclerView.adapter = PartyDetailedRecyclerAdapter(
                    partyMembers.map { member ->
                        return@map PartyMemberItem(
                            name = member.firstName + " " + member.lastName,
                            image = R.drawable.ic_account,
                            constituency = "it.constituency",
                            seatNumber = member.seatNo,
                            onClick = {}
                        )
                    })
            }

        })
    }
}