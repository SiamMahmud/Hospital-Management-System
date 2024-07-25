package com.example.hospitalmanagement.presentation.dashboard
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentProfileBinding
import com.example.hospitalmanagement.presentation.MainActivity
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.example.hospitalmanagement.presentation.util.SharePreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var sharedPrefs: SharePreferenceUtil
    @Inject
    lateinit var activityUtil:HMSActivityUtil
    private lateinit var binding: FragmentProfileBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(false)
        binding.btnSignOut.setOnClickListener {
            logout(it)
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun logout(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            sharedPrefs.setAuthToken("")
            activity?.let {
                startActivity(MainActivity.getLaunchIntent(it))
            }
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}