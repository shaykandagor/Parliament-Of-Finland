package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.databinding.FragmentHomeBinding

/**
 * This class defines what the user can search for about the parliament
 * It contains the members, political parties, about the parliament and comments from users
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

class HomeFragment: Fragment() {
    lateinit var navController: NavController
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        addEventListeners()
        navController = findNavController()
        return binding.root
    }
    private fun addEventListeners(){
        binding.politicalPartyCard.setOnClickListener{
            navController.navigate(R.id.partiesFragment)
        }
        binding.aboutParliamentCard.setOnClickListener{
            navController.navigate(R.id.aboutParliamentFragment)
        }
        binding.membersCard.setOnClickListener{

            navController.navigate(R.id.action_homeFragment_to_allMembersFragment)
        }
    }
}