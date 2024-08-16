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
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorListBinding
import com.example.hospitalmanagement.presentation.adapter.AdminViewDoctorListAdapter
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.model.MedicineInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminViewDoctorListFragment : Fragment() {
    val actionAddDoctorList =
        Navigation.createNavigateOnClickListener(R.id.action_adminViewDoctorListFragment_to_adminAddDoctorFragment)

    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding: FragmentAdminViewDoctorListBinding
    private lateinit var doctorArray: ArrayList<DoctorInfo>
    private lateinit var adapter: AdminViewDoctorListAdapter
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_view_doctor_list, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        doctorArray = arrayListOf()

        binding.doctorListRecycle.layoutManager = LinearLayoutManager(activity)
        adapter = AdminViewDoctorListAdapter(doctorArray)
        binding.doctorListRecycle.adapter = adapter

        binding.searchDoctor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        adapter.onItemClick = {
            var bundle = Bundle()
            bundle.putString("id",it.doctorId)
            bundle.putString("name",it.name)
            bundle.putString("email",it.email)
            bundle.putString("phoneNumber",it.phoneNumber)
            bundle.putString("doctorSpecialization",it.doctorSpecialization)
            bundle.putString("doctorDegree",it.doctorDegree)
            bundle.putString("doctorInstuition",it.doctorInstuition)
            findNavController().navigate(R.id.action_adminViewDoctorListFragment_to_adminViewDoctorDetailsFragment, bundle)
        }
        return binding.root
    }
    private fun searchList(text: String) {
        val searchList = ArrayList<DoctorInfo>()
        for (doctor in doctorArray){
            if (doctor.name?.lowercase()?.contains(text.lowercase())==true){
                searchList.add(doctor)
            }
        }
        adapter.searchDataList(searchList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDoctor()
    }

    private fun loadDoctor() {
        activityUtil.setFullScreenLoading(true)
        database = FirebaseDatabase.getInstance().getReference("Doctor")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                activityUtil.setFullScreenLoading(false)
                if (snapshot.exists()){
                    doctorArray.clear()
                    for (snap in snapshot.children){
                        val doctor = snap.getValue(DoctorInfo::class.java)
                        doctor?.doctorId = snap.key
                        if (doctor != null && !doctorArray.contains(doctor)) {
                            doctorArray.add(doctor)
                        }

                    }
                    adapter.searchDataList(doctorArray)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
