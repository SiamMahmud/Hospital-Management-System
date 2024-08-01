package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminBloodBinding
import com.example.hospitalmanagement.presentation.adapter.AdminBloodBankAdapter
import com.example.hospitalmanagement.presentation.model.BloodBankDetails
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminBloodFragment : Fragment() {
    val actionAddBloodList = Navigation.createNavigateOnClickListener(R.id.action_admibBloodFragment_to_addBloodBankFragment)
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentAdminBloodBinding
    private lateinit var bloodBankArray: ArrayList<BloodBankDetails>
    private lateinit var adapter: AdminBloodBankAdapter
    lateinit var arrayL: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_blood, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.bloodBankListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf("Siam, B+", "Ashraful, AB+")
        bloodBankArray = arrayListOf()
        adapter = AdminBloodBankAdapter(bloodBankArray)
        binding.bloodBankListRecycle.adapter = adapter
        adapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("donorName", it.donorName)
            bundle.putString("bloodGroupType", it.bloodGroupType)
            findNavController().navigate(R.id.action_admibBloodFragment_to_adminBloodBankDetailsFragment, bundle)
        }

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        getUserdata()
        return binding.root
    }

    private fun getUserdata() {
        for(i in arrayL.indices){
            val details = arrayL[i].split(", ")
            val bloodBank = BloodBankDetails(details[0], details[1])
            bloodBankArray.add(bloodBank)
        }
        adapter.notifyDataSetChanged()
    }
    }