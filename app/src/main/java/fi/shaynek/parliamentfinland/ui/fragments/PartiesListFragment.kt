package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.shaynek.parliamentfinland.R

/**
 * This class defines the parties list
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */
class PartiesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_parties, container, false)
    }


}