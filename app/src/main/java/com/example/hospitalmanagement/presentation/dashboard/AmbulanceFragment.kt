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
import com.example.hospitalmanagement.databinding.FragmentAmbulanceBinding
import com.example.hospitalmanagement.presentation.adapter.AmbulanceListAdapter
import com.example.hospitalmanagement.presentation.model.AmbulanceDetails
import com.example.hospitalmanagement.presentation.model.BloodBankDetails
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AmbulanceFragment : Fragment() {
    val actionAddAmbulanceList =
        Navigation.createNavigateOnClickListener(R.id.action_ambulanceFragment_to_adminAddAmbulanceFragment)
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentAmbulanceBinding
    private lateinit var ambulanceArray: ArrayList<AmbulanceDetails>
    private lateinit var adapter: AmbulanceListAdapter
    lateinit var arrayL: Array<String>
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ambulance, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.ambulanceListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayL = arrayOf()
        ambulanceArray = arrayListOf()
        adapter = AmbulanceListAdapter(ambulanceArray)
        binding.ambulanceListRecycle.adapter = adapter
        adapter.onItemClick = {
            var bundle = Bundle()
            bundle.putString("driverName",it.driverName)
            bundle.putString("ambulLocation",it.ambulLocation)
            bundle.putString("licenceNumber",it.licenceNumber)
            bundle.putString("phoneNumber",it.phoneNumber)
            findNavController().navigate(R.id.action_ambulanceFragment_to_ambulanceDetailsFragment, bundle)
        }

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.searchAmbulance.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        return binding.root
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<AmbulanceDetails>()
        for (ambulance in ambulanceArray){
            if (ambulance.ambulLocation?.lowercase()?.contains(text.lowercase())==true){
                searchList.add(ambulance)
            }
        }
        adapter.searchDataList(searchList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAmbulance()
    }

    private fun loadAmbulance() {
        activityUtil.setFullScreenLoading(true)
        database = FirebaseDatabase.getInstance().getReference("Ambulance")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                activityUtil.setFullScreenLoading(false)
                if (snapshot.exists()){
                    for (snap in snapshot.children){
                        val ambulance = snap.getValue(AmbulanceDetails::class.java)
                        ambulance?.ambulanceId = snap.key
                        if (!ambulanceArray.contains(ambulance)){
                            ambulanceArray.add(ambulance!!)
                        }
                    }
                    adapter.searchDataList(ambulanceArray)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(),error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}