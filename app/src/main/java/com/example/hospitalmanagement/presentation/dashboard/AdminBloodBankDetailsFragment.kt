package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminBloodBankDetailsBinding


class AdminBloodBankDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAdminBloodBankDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_blood_bank_details, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.donorN.text = requireArguments().getString("donorName")
        binding.aGroupTypeEt.text = requireArguments().getString("bloodGroupType")

        return binding.root
    }

}