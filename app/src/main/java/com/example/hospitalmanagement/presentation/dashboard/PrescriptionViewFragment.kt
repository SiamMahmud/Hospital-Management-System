package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminBloodBinding
import com.example.hospitalmanagement.databinding.FragmentPrescriptionViewBinding
import com.example.hospitalmanagement.presentation.adapter.AdminBloodBankAdapter
import com.example.hospitalmanagement.presentation.adapter.AdminPrescribeAdapter
import com.example.hospitalmanagement.presentation.model.BloodBankDetails
import com.example.hospitalmanagement.presentation.model.Prescription
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrescriptionViewFragment : Fragment() {

    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentPrescriptionViewBinding
    private lateinit var prescribeArray: ArrayList<Prescription>
    private lateinit var adapter: AdminPrescribeAdapter
    lateinit var arrayL: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prescription_view, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.prescribeListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf("Siam, Dr.Lina", "Ashraful, Dr.Zon")
        prescribeArray = arrayListOf()
        adapter = AdminPrescribeAdapter(prescribeArray)
        binding.prescribeListRecycle.adapter = adapter
        adapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("pPatientName", it.pPatientName)
            bundle.putString("pDocName", it.pDocName)
            findNavController().navigate(R.id.action_prescriptionViewFragment_to_prescriptionDetailsFragment, bundle)
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
            val prescribe = Prescription(details[0], details[1])
            prescribeArray.add(prescribe)
        }
        adapter.notifyDataSetChanged()
    }
}