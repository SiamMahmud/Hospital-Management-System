package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentMedicineDetailsBinding


class MedicineDetailsFragment : Fragment() {
    private lateinit var binding : FragmentMedicineDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicine_details, container, false)
        binding.model = this
        binding.nameTv.text = requireArguments().getString("mediName")
        binding.priceTv.text = requireArguments().getString("mediPrice")
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}