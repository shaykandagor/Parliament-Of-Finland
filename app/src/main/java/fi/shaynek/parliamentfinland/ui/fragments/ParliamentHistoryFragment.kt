package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.shaynek.parliamentfinland.databinding.FragmentParliamentHistoryBinding

/**
 * This fragment displays brief information about the parliament of Finland and its history
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */


class ParliamentHistoryFragment : Fragment() {
    lateinit var binding: FragmentParliamentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParliamentHistoryBinding.inflate(inflater)
        return binding.root
    }

}