package com.example.hospitalmanagement.presentation.login

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentLoginInputBinding
import com.example.hospitalmanagement.presentation.MainActivity
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.example.hospitalmanagement.presentation.util.SharePreferenceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginInputFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil

    @Inject
    lateinit var sharedPrefs: SharePreferenceUtil
    val actionForgot = Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginForgetPasswordFragment)
    val actionSignUp = Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginCreateFragment)

    private lateinit var binding : FragmentLoginInputBinding

    private lateinit var viewModel: LoginViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_input, container, false)
        binding.model = this

        binding.loginErrorTv.visibility = View.INVISIBLE

        binding.btnSignIn.setOnClickListener {
            Handler().postDelayed({
                sharedPrefs.setAuthToken("Siam")
                activity?.let {
                    startActivity(MainActivity.getLaunchIntent(it))
                }
            }, 3000)
        }

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
}
