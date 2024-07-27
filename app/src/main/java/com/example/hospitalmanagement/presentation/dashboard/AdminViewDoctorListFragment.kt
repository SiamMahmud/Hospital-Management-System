package com.example.hospitalmanagement.presentation.dashboard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorListBinding
import com.example.hospitalmanagement.presentation.adapter.AdminViewDoctorListAdapter
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminViewDoctorListFragment : Fragment() {
    val actionAddDoctorList =
        Navigation.createNavigateOnClickListener(R.id.action_adminViewDoctorListFragment_to_adminAddDoctorFragment)
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding: FragmentAdminViewDoctorListBinding
    private lateinit var doctorArray: ArrayList<DoctorInfo>
    private lateinit var adapter: AdminViewDoctorListAdapter
    lateinit var arr: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_admin_view_doctor_list,
            container,
            false
        )
        binding.model = this
        activityUtil.hideBottomNavigation(true)

        binding.doctorListRecycle.layoutManager = LinearLayoutManager(activity)

        arr = arrayOf(
            "Siam, Medicine",
            "Ashraful, Physiology"
        )

        doctorArray = arrayListOf()
        adapter = AdminViewDoctorListAdapter(doctorArray)
        binding.doctorListRecycle.adapter = adapter

        getUserdata()

        return binding.root
    }

    private fun getUserdata() {
        for (i in arr.indices) {
            val details = arr[i].split(", ")
            val doctor = DoctorInfo(details[0], details[1])
            doctorArray.add(doctor)
        }
        adapter.notifyDataSetChanged()
    }
}
