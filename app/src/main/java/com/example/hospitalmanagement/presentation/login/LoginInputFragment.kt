package com.example.hospitalmanagement.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentLoginInputBinding

class LoginInputFragment : Fragment() {
    val actionForgot =
        Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginForgetPasswordFragment)
    val actionSignUp =
        Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginCreateFragment)

    private lateinit var binding : FragmentLoginInputBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_input, container, false)
        binding.model = this

        binding.loginErrorTv.visibility = View.INVISIBLE

        return binding.root
    }
}