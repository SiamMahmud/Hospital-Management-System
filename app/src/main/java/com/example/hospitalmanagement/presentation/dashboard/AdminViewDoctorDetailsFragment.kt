package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorDetailsBinding
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorListBinding

class AdminViewDoctorDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminViewDoctorDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_view_doctor_details, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.nameTv.text = requireArguments().getString("name")
        binding.doctorSpecializationTv.text = requireArguments().getString("specialty")
        return binding.root
    }
}
