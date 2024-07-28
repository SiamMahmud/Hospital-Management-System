package com.example.hospitalmanagement.presentation.dashboard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminAddAmbulanceBinding
import com.example.hospitalmanagement.databinding.FragmentAdminAddDoctorBinding

class AdminAddAmbulanceFragment : Fragment() {
    private lateinit var binding: FragmentAdminAddAmbulanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_ambulance, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}