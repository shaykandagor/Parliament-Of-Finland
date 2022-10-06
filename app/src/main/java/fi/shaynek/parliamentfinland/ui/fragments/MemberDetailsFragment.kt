package fi.shaynek.parliamentfinland.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.network.ParliamentApiClient
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.FragmentMemberDetailsBinding


class MemberDetailsFragment : Fragment() {
    lateinit var viewModel: MemberDetailsViewModel
    lateinit var binding: FragmentMemberDetailsBinding
    lateinit var netDataHandler: Handler
    lateinit var imgLoaderHandler: Handler


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
                (requireActivity().application as MainApplication).membersRepository,
                ParliamentApiClient.retrofitService
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

        netDataHandler.post {
            viewModel.fetchBasicData()
            viewModel.fetchExtraData()

        }

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
            binding.borneYear.setText(it[0].bornYear)
            binding.constituency.setText(it[0].constituency)
        })
        viewModel.img.observe(requireActivity(), Observer{
            if (it != null){
                binding.photo.setImageBitmap(it)
            }else{
                binding.photo.setImageResource(R.drawable.ic_account)
            }
        })
    }



}