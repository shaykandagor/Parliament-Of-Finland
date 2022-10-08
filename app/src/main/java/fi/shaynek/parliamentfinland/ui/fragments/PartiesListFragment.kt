package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.shaynek.parliamentfinland.R

/**
 * A simple [Fragment] subclass.
 * Use the [PartiesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartiesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parties, container, false)
    }


}