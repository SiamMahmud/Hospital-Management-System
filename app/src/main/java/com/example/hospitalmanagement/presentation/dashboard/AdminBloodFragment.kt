package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminBloodBinding
import com.example.hospitalmanagement.presentation.adapter.AdminBloodBankAdapter
import com.example.hospitalmanagement.presentation.model.BloodBankDetails
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_blood, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.bloodBankListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf()
        bloodBankArray = arrayListOf()
        adapter = AdminBloodBankAdapter(bloodBankArray)
        binding.bloodBankListRecycle.adapter = adapter
        adapter.onItemClick = {
            var bundle = Bundle()
            bundle.putString("bloodBankId",it.bloodBankId)
            bundle.putString("donorName",it.donorName)
            bundle.putString("bloodGroupType",it.bloodGroupType)
            bundle.putString("donorPhoneNumber",it.donorPhoneNumber)
            findNavController().navigate(R.id.action_admibBloodFragment_to_adminBloodBankDetailsFragment, bundle)
        }
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchBlood.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    searchList(text)
                }
                return true
            }
        })

        return binding.root
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<BloodBankDetails>()
        for (blood in bloodBankArray){
            if (blood.bloodGroupType?.lowercase()?.contains(text.lowercase())==true){
                searchList.add(blood)
            }
        }
        adapter.searchDataList(searchList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBloodBank()
    }

    private fun loadBloodBank() {
        activityUtil.setFullScreenLoading(true)
        database = FirebaseDatabase.getInstance().getReference("Blood Bank")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                activityUtil.setFullScreenLoading(false)
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val blood = snap.getValue(BloodBankDetails::class.java)
                        blood?.bloodBankId = snap.key
                        if (!bloodBankArray.contains(blood)){
                            bloodBankArray.add(blood!!)
                        }
                    }
                    adapter.searchDataList(bloodBankArray)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


}