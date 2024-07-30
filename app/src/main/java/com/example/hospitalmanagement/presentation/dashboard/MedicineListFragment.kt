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
import com.example.hospitalmanagement.databinding.FragmentMedicineBinding
import com.example.hospitalmanagement.presentation.adapter.AdminViewMedicineListAdapter
import com.example.hospitalmanagement.presentation.model.MedicineInfo

class MedicineListFragment : Fragment() {
    private lateinit var binding : FragmentMedicineBinding
    private lateinit var adapter: AdminViewMedicineListAdapter
    private lateinit var medicineArray: ArrayList<MedicineInfo>
    lateinit var arrayMediList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicine, container, false)
        binding.model = this

        binding.medicineListRecycle.layoutManager = LinearLayoutManager(activity)
        arrayMediList = arrayOf("Napa Extra, 2RM PP", "Paracetamol, 5RM PP")
        medicineArray = arrayListOf()
        adapter = AdminViewMedicineListAdapter(medicineArray)
        binding.medicineListRecycle.adapter = adapter
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        getUserdata()
        return binding.root
    }

    private fun getUserdata() {
        for(i in arrayMediList.indices){
            val details = arrayMediList[i].split(", ")
            val medicine = MedicineInfo(details[0], details[1])
            medicineArray.add(medicine)
        }
        adapter.notifyDataSetChanged()
    }

}