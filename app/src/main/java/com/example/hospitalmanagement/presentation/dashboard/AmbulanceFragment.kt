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
import com.example.hospitalmanagement.databinding.FragmentAmbulanceBinding
import com.example.hospitalmanagement.presentation.adapter.AmbulanceListAdapter
import com.example.hospitalmanagement.presentation.model.AmbulanceDetails
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AmbulanceFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentAmbulanceBinding
    private lateinit var ambulanceArray: ArrayList<AmbulanceDetails>
    private lateinit var adapter: AmbulanceListAdapter
    lateinit var arrayL: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ambulance, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.ambulanceListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf("Siam, Medicine", "Ashraful, Physiology")
        ambulanceArray = arrayListOf()
        adapter = AmbulanceListAdapter(ambulanceArray)
        binding.ambulanceListRecycle.adapter = adapter
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        getUserdata()
        return binding.root
    }
    private fun getUserdata() {
       for(i in arrayL.indices){
           val details = arrayL[i].split(", ")
           val ambulance = AmbulanceDetails(details[0], details[1])
           ambulanceArray.add(ambulance)
       }
        adapter.notifyDataSetChanged()
    }
}