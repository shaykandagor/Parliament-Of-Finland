package fi.shaynek.parliamentfinland.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentMemberDetailsBinding
import fi.shaynek.parliamentfinland.utils.Shared

/**
 * This class defines details about members of parliament
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */


class MemberDetailsFragment : Fragment() {
    private lateinit var viewModel: MemberDetailsViewModel
    private lateinit var binding: FragmentMemberDetailsBinding
    private var hetekaId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberDetailsBinding.inflate(inflater)
        hetekaId = arguments?.getString("hetekaId")?.toIntOrNull()
        viewModel = ViewModelProvider(
            requireActivity(), MemberDetailsViewModelFactory(
                (requireActivity().application as MainApplication).membersRepository
            )
        )[MemberDetailsViewModel::class.java]

        initUiComponents()
        addEventListeners()
        return binding.root
    }

    /**
     * observes basic and extra live data details and updates the UI controller with latest data
     */
    private fun initUiComponents() {
        viewModel.basicData.observe(requireActivity(), Observer {
            hetekaId?.let { _id ->
                val memberBasic = getMemberBasic(_id, it)
                if (memberBasic != null) {
                    populateBasicDetailFields(memberBasic as MembersBasicData)
                }
            }
        })
        viewModel.extraData.observe(requireActivity(), Observer {
            hetekaId?.let { _id ->
                getMemberExtra(_id, it)
                    ?.let { extra ->
                        populateExtraDetailFields(extra)
                    }
            }
        })

    }

    private fun getMemberExtra(
        hetekaId: Int,
        membersExtraData: List<MembersExtraData>
    ): MembersExtraData? {
        val extra = membersExtraData.find { current ->
            return@find hetekaId == current.hetekaId
        }
        return extra
    }

    /**
     * searches for a member with the specified hetekaId from the list of members and returns it if found otherwise returns null
     */
    private fun getMemberBasic(
        hetekaId: Int,
        membersBasicData: List<MembersBasicData>
    ): MembersBasicData? {

        return membersBasicData.find { basicData ->
            return@find basicData.hetekaId == hetekaId

        }
    }

    /**
     *
     */

    private fun populateBasicDetailFields(basicData: MembersBasicData) {
        binding.hetekaId.setText(basicData.hetekaId.toString())
        binding.firstName.setText(basicData.firstName.toString())
        binding.lastName.setText(basicData.lastName.toString())
        binding.seatNumber.setText(basicData.seatNo.toString())
        binding.party.setText(basicData.party)
        binding.minister.isChecked = basicData.minister
        binding.photo.load("${Shared.IMG_BASE_URL}${basicData.photoUrl}")

    }

    private fun populateExtraDetailFields(extraData: MembersExtraData) {

        binding.twitter.setText(extraData.twitterHandle)
        binding.bornYear.setText(extraData.bornYear.toString())
        binding.constituency.setText(extraData.constituency)

    }

    private fun addEventListeners() {
        binding.twitter.setOnClickListener {
            binding.twitter.text?.let {
                if (it.isNotBlank()){
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(binding.twitter.text.toString())
                    startActivity(intent)
                }

            }
        }

    }
}