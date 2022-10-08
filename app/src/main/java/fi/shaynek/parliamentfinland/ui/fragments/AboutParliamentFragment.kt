package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.shaynek.parliamentfinland.databinding.FragmentAboutParliamentBinding


class AboutParliamentFragment : Fragment() {
    lateinit var binding: FragmentAboutParliamentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutParliamentBinding.inflate(inflater)
        return binding.root
    }

}