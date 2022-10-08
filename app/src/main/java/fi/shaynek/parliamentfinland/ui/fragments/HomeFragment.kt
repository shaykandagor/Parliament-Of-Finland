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
//        val navHost = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHost.navController
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
//            navController.navigate(R.id.)
        }
    }
}