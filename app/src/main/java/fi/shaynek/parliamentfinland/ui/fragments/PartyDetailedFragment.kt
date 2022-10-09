package fi.shaynek.parliamentfinland.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.adapter.PartyDetailedRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.models.Parliament
import fi.shaynek.parliamentfinland.data.models.PartyMemberItem
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentPartyDetailedBinding
import fi.shaynek.parliamentfinland.utils.Shared

/**
 * it defines the members of the selected party by the user  and the total members contained in it
 * Presents party details such as party leader, party logo, total party members and name of party
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

class PartyDetailedFragment : Fragment() {
    private lateinit var viewModel: MemberDetailsViewModel
    lateinit var binding: FragmentPartyDetailedBinding
    lateinit var navController: NavController
    private var currParty: String? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    /**
     * observes changes in database for party details and updates the UI controllers with latest data
     * Also updates the party members' recycler view adapter with a new adapter containing latest data from the database
     * dataclass PartyMemberItem is used to pass recyclerview items' data
     */

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun addObservers() {
        currParty?.let { curr ->
            viewModel.basicData.observe(requireActivity(), Observer { members ->
                val parliament = Parliament(members)
                val partyMembers = parliament.partyMembers(curr)
                binding.partyDetailedIcon.setImageResource(parliament.partyLogo(curr))
                binding.partyDetailedLeader.text =
                    "Leader: " + partyMembers.first().firstName + " " + partyMembers.first().lastName
                binding.partyDetailedTitle.text = "Party: $curr"
                binding.partyDetailedMembersCount.text =
                    "Total Members: " + partyMembers.size.toString()
                val items = partyMembers.map { member ->
                    val item = PartyMemberItem(
                        name = member.firstName + " " + member.lastName,
                        image = "${Shared.IMG_BASE_URL}${member.photoUrl}",
                        constituency = getConstituency(member.hetekaId),
                        seatNumber = member.seatNo,
                        onClick = {
                            var data = Bundle()
                            data.putString("hetekaId", member.hetekaId.toString())
                            navController.navigate(R.id.memberDetailsFragment, data)

                        }
                    )
                    return@map item
                }
                binding.partyDetailedRecyclerView.adapter = PartyDetailedRecyclerAdapter(items)
            })
        }

    }
    private fun getConstituency(hetekaId : Int): String{
        var extraData = viewModel.extraData.value?.find {
            return@find it.hetekaId == hetekaId
        }
       return extraData?.constituency?: ""
    }
}