package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.shaynek.parliamentfinland.databinding.FragmentAboutParliamentBinding

/**
 * Its dipslays information and history about parliament of Finland
 * @author Shayne Kandagor
 * @studentId 2112916
 * @Version 3.0
 * @since 04.09.2022
 */


class AboutParliamentFragment : Fragment() {
    lateinit var binding: FragmentAboutParliamentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAboutParliamentBinding.inflate(inflater)
        return binding.root
    }


}