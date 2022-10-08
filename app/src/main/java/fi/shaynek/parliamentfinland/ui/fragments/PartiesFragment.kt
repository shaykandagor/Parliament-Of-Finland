package dev.vstec.parliament2.ui.fragments

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
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dev.vstec.parliament2.MainActivity
import dev.vstec.parliament2.R
import dev.vstec.parliament2.adapters.PartiesRecyclerAdapter
import dev.vstec.parliament2.app.MainApplication
import dev.vstec.parliament2.data.models.Parliament
import dev.vstec.parliament2.data.models.PartyItem
import dev.vstec.parliament2.data.viewmodels.MemberDetailsViewModel
import dev.vstec.parliament2.data.viewmodels.MemberDetailsViewModelFactory
import dev.vstec.parliament2.databinding.FragmentPartiesBinding

class PartiesFragment : Fragment() {
    lateinit var binding: FragmentPartiesBinding
    private lateinit var viewModel: MemberDetailsViewModel
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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

    private fun addObservers() {
        val partyIcons: Array<Int> = arrayOf(
            R.drawable.ic_account,
            android.R.drawable.ic_delete,
            android.R.drawable.ic_btn_speak_now,
            android.R.drawable.ic_lock_idle_alarm,

            )
        viewModel.basicData.observe(requireActivity(), Observer { members ->
            val parlament = Parliament(members)
            val parties = parlament.parties()
            binding.partiesRecyclerView.adapter = PartiesRecyclerAdapter(parties.map { party ->
                return@map PartyItem(
                    title = party,
                    head = "${
                        parlament.partyMembers(party).first().firstName
                    } ${parlament.partyMembers(party).first().lastName}",
                    icon = partyIcons.random(),
                    count = parlament.partyMembers(party).size,
                    onClick = {
                        MainActivity.SHARED = party
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
//                    viewModel.applyFilter(it.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
}