package com.example.hospitalmanagement.presentation.dashboard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentHomeBinding
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val actionDoctorList =
        Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_adminViewDoctorListFragment)
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(false)
        return binding.root
    }
}