package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.adapter.PartiesRecyclerAdapter
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.models.Parliament
import fi.shaynek.parliamentfinland.data.models.PartyItem
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentPartiesBinding

/**
 * It defines the political parties of the members
 * It also defines the logos of the members, the total number of members in that particular party
 * and the members of the particular party when clicked by the users
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

class PartiesFragment : Fragment() {
    lateinit var binding: FragmentPartiesBinding
    private lateinit var viewModel: MemberDetailsViewModel
    lateinit var navController: NavController
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPartiesBinding.inflate(inflater)
        viewModel = ViewModelProvider(
            requireActivity(), MemberDetailsViewModelFactory(
                (requireActivity().application as MainApplication).membersRepository
            )
        )[MemberDetailsViewModel::class.java]
        addObservers()
        addEventListener()
        navController = findNavController()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addObservers() {

        viewModel.basicData.observe(requireActivity(), Observer { members ->
            val parlament = Parliament(members)
            val parties = parlament.parties()
            binding.partiesRecyclerView.adapter = PartiesRecyclerAdapter(parties.map { party ->
                return@map PartyItem(
                    title = party,
                    head = "${
                        parlament.partyMembers(party).first().firstName
                    } ${parlament.partyMembers(party).first().lastName}",
                    icon = parlament.partyLogo(party),
                    count = parlament.partyMembers(party).size,
                    onClick = {
                        Toast.makeText(requireActivity(), party, Toast.LENGTH_LONG).show()
                        val data = Bundle()
                        data.putString("party", party)
                        navController.navigate(R.id.partyDetailedFragment, data)
                    }
                )
            })
        })

    }

    private fun addEventListener() {
        binding.searchParty.setOnClickListener {

            if (binding.searchCard.visibility == VISIBLE) {
                binding.searchCard.visibility = GONE
                binding.searchParty.setImageResource(android.R.drawable.ic_menu_search)

            } else {
                binding.searchCard.visibility = VISIBLE
                binding.searchParty.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            }

        }
        binding.partySearchString.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}