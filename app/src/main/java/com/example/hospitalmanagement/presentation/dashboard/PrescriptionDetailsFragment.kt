package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentPrescriptionDetailsBinding

class PrescriptionDetailsFragment : Fragment() {
    private lateinit var binding : FragmentPrescriptionDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prescription_details, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.pNameTv.text = requireArguments().getString("pPatientName")
        binding.dNameEt.text = requireArguments().getString("pDocName")

        return binding.root
    }
}