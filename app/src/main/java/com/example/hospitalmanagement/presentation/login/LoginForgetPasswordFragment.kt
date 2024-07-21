package com.example.hospitalmanagement.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentLoginForgetPasswordBinding

class LoginForgetPasswordFragment : Fragment() {
    val actionVarify =
        Navigation.createNavigateOnClickListener(R.id.action_loginForgetPasswordFragment_to_loginVerifyPasswordFragment)
    private lateinit var binding: FragmentLoginForgetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login_forget_password,
            container,
            false
        )
        binding.model = this

        return binding.root
    }
}