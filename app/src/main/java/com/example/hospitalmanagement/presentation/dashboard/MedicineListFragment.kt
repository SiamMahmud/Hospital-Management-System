package com.example.hospitalmanagement.presentation.dashboard

import android.icu.text.SimpleDateFormat
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
import com.example.hospitalmanagement.databinding.FragmentMedicineBinding
import com.example.hospitalmanagement.presentation.adapter.AdminViewMedicineListAdapter
import com.example.hospitalmanagement.presentation.model.MedicineInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MedicineListFragment : Fragment() {
    val actionAddMedicineList = Navigation.createNavigateOnClickListener(R.id.action_medicineListFragment_to_addMedicineFragment)
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding : FragmentMedicineBinding
    private lateinit var adapter: AdminViewMedicineListAdapter
    private lateinit var medicineArray: ArrayList<MedicineInfo>
    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicine, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        medicineArray = arrayListOf()
        binding.medicineListRecycle.layoutManager = LinearLayoutManager(activity)
        adapter = AdminViewMedicineListAdapter(medicineArray)
        binding.medicineListRecycle.adapter = adapter

        binding.searchMedicine.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        adapter.onItemClick = {
            var bundle = Bundle()
            bundle.putString("id",it.medicineId)
            bundle.putString("name",it.name)
            bundle.putString("company",it.company)
            bundle.putString("price",it.price)
            bundle.putString("date",it.date)
            bundle.putString("self",it.self)
            bundle.putString("row",it.row)
            bundle.putString("column",it.column)
            bundle.putString("details",it.details)
            findNavController().navigate(R.id.action_medicineListFragment_to_medicineDetailsFragment, bundle)
        }
        return binding.root
    }

    private fun searchList(text: String) {
        val searchList = ArrayList<MedicineInfo>()
        for (medicine in medicineArray){
            if (medicine.name?.lowercase()?.contains(text.lowercase())==true){
                searchList.add(medicine)
            }
        }
        adapter.searchDataList(searchList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMedicine()
    }

    private fun loadMedicine() {
        activityUtil.setFullScreenLoading(true)
        database = FirebaseDatabase.getInstance().getReference("Medicine")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                activityUtil.setFullScreenLoading(false)
                if (snapshot.exists()) {
                    medicineArray.clear()
                    for (snap in snapshot.children) {
                        val medicine = snap.getValue(MedicineInfo::class.java)
                        medicine?.medicineId = snap.key
                        if (medicine != null && !medicineArray.contains(medicine) && isExpiredDate(medicine.date)) {
                            medicineArray.add(medicine)
                        }
                    }
                    adapter.searchDataList(medicineArray)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isExpiredDate(dateString: String?): Boolean {
        if (dateString.isNullOrEmpty()) {
            return false
        }
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date: Date = try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return Date().before(date)
    }
}