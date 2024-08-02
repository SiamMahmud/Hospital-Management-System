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
import com.example.hospitalmanagement.databinding.FragmentAppointmentBinding
import com.example.hospitalmanagement.presentation.adapter.AppointmentAdapter
import com.example.hospitalmanagement.presentation.model.AppointmentInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentAppointmentBinding
    private lateinit var appointArray: ArrayList<AppointmentInfo>
    private lateinit var adapter: AppointmentAdapter
    lateinit var arrayL: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.appointmentListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf("Siam, 22 December", "Ashraful, 21 August")
        appointArray = arrayListOf()
        adapter = AppointmentAdapter(appointArray)
        binding.appointmentListRecycle.adapter = adapter
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        getUserdata()
        return binding.root
    }

        private fun getUserdata() {
            for(i in arrayL.indices){
                val details = arrayL[i].split(", ")
                val medicine = AppointmentInfo(details[0], details[1])
                appointArray.add(medicine)
            }
            adapter.notifyDataSetChanged()
        }

}