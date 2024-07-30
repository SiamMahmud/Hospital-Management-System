package com.example.hospitalmanagement.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentMedicineBinding
import com.example.hospitalmanagement.presentation.adapter.AdminViewMedicineListAdapter
import com.example.hospitalmanagement.presentation.model.MedicineInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicineListFragment : Fragment() {
    val actionAddMedicineList = Navigation.createNavigateOnClickListener(R.id.action_medicineListFragment_to_addMedicineFragment)
    private lateinit var binding : FragmentMedicineBinding
    private lateinit var adapter: AdminViewMedicineListAdapter
    private lateinit var medicineArray: ArrayList<MedicineInfo>
    lateinit var arrayMediList: Array<String>
    @Inject
    lateinit var activityUtil: HMSActivityUtil
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
        activityUtil.hideBottomNavigation(true)
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