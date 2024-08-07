package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminAddDoctorBinding
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorListBinding
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import javax.inject.Inject


class AdminAddDoctorFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddDoctorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_doctor, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }


}


