package dev.vstec.parliament2.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.vstec.parliament2.R
import dev.vstec.parliament2.app.MainApplication
import dev.vstec.parliament2.data.entity.MembersBasicData
import dev.vstec.parliament2.data.entity.MembersExtraData
import dev.vstec.parliament2.data.viewmodels.MemberDetailsViewModel
import dev.vstec.parliament2.data.viewmodels.MemberDetailsViewModelFactory
import dev.vstec.parliament2.databinding.FragmentMemberDetailsBinding
import dev.vstec.parliament2.services.ParliamentApiClient
import kotlin.properties.Delegates


class MemberDetailsFragment : Fragment() {
    private lateinit var viewModel: MemberDetailsViewModel
    private lateinit var binding: FragmentMemberDetailsBinding
    private lateinit var netDataHandler: Handler
    private lateinit var imgLoaderHandler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMemberDetailsBinding.inflate(inflater)
        netDataHandler = Handler()
        imgLoaderHandler = Handler()

        /*
            //if no boding used
            val v = inflater.inflate(R.layout.fragment_member_details, container, false)

            val xx: TextView = v.findViewById(R.id.ous)
            val inc: Button = v.findViewById(R.id.inc)

            // if instanciating view model directly without the factory bellow line
            viewModel = ViewModelProvider(requireActivity())[MemberDetailsViewModel::class.java]

            //when isntanciating with the factory then you need to pass the the factory class to
            the provider as illustrated in the code


            inc.setOnClickListener {
                viewModel.inC()
                viewModel.fetchBasicData()
                xx.text = viewModel.greeting.toString()
                Toast.makeText(container?.context, "Click", Toast.LENGTH_LONG).show()
            }

            */
        viewModel = ViewModelProvider(
            requireActivity(), MemberDetailsViewModelFactory(
                (requireActivity().application as MainApplication).membersRepository
            )
        )[MemberDetailsViewModel::class.java]

        /*
        When using model view withoout live data you have to explicitly update the form control
        after updating its value in the viewmodel as show in bellow code snippet

        xx.text = viewModel.count.toString()

        onClick(){
            viewModel.inCrement()
            xx.text = viewModel.count.toString()
        }

        */

        /*
        When using live data which adds an observer patter that update the value on change then
        you need to observer every form entry e.g

        viewModel.data.observe(viewLifecycleOwner, Observer {
            xx.text = it.toString()
        })
        dISADVERNATAGE IS THAT YOU HAVE TO KEEP TRACK OF EVERY FIELD IN VIEW MODEL AND UI CONTROLER
        AND OBSERVER THEM.tO SOLVE THIS WE USE DATABINDING AS ILLUSTRATED



         */
        initUiComponents()
        addEventHandlers()
        return binding.root
    }

    private fun addEventHandlers() {
        /*
        binding.inc.setOnClickListener{
//            viewModel.fetchExtraData()
            viewModel.fetchBasicData()

        }*/
    }

    private fun initUiComponents() {
        Log.d("Ous", viewModel.basicData.value.toString())
        netDataHandler.post {
            viewModel.syncFetch(requireActivity())

        }
        viewModel.basicData.observe(requireActivity(), Observer{
            var basic = it[0]

//            populateFields()
        })
        /*
        viewModel.basicData.observe(requireActivity()) {
            if (it.isNotEmpty()) {
//                viewModel.loadImage(it[0].photoUrl)
                binding.hetekaId.setText(it[0].hetekaId.toString())
                binding.firstName.setText(it[0].firstName.toString())
                binding.lastName.setText(it[0].lastName.toString())
                binding.seatNumber.setText(it[0].seatNo.toString())
                binding.party.setText(it[0].party)
                binding.minister.isChecked = it[0].minister
            }
        }

        viewModel.extraData.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                binding.twitter.setText(it[0].twitterHandle)
                binding.borneYear.setText(it[0].bornYear.toString())
                binding.constituency.setText(it[0].constituency)
            }
        }*/
        /*
        viewModel.observeStatus(requireActivity())

        viewModel.basicDataNet.observe(requireActivity(), Observer {
            viewModel.loadImage(it[0].photoUrl)
            binding.hetekaId.setText(it[0].hetekaId.toString())
            binding.firstName.setText(it[0].firstName.toString())
            binding.lastName.setText(it[0].lastName.toString())
            binding.seatNumber.setText(it[0].seatNo.toString())
            binding.party.setText(it[0].party)
            binding.minister.isChecked = it[0].minister
        })
        viewModel.extraDataNet.observe(requireActivity(), Observer {
            binding.twitter.setText(it[0].twitterHandle)
            binding.borneYear.setText(it[0].bornYear.toString())
            binding.constituency.setText(it[0].constituency)
        })
        viewModel.img.observe(requireActivity(), Observer{
            if (it != null){
                binding.photo.setImageBitmap(it)
            }else{
                binding.photo.setImageResource(R.drawable.ic_account)
            }
        })

         */
    }
    private fun populateFields(basicData:MembersBasicData, extraData:MembersExtraData){
        binding.hetekaId.setText(basicData.hetekaId.toString())
        binding.firstName.setText(basicData.firstName.toString())
        binding.lastName.setText(basicData.lastName.toString())
        binding.seatNumber.setText(basicData.seatNo.toString())
        binding.party.setText(basicData.party)
        binding.minister.isChecked = basicData.minister

        binding.twitter.setText(extraData.twitterHandle)
        binding.borneYear.setText(extraData.bornYear.toString())
        binding.constituency.setText(extraData.constituency)
    }


}