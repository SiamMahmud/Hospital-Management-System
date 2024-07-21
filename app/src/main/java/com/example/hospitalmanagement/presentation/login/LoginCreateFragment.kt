package com.example.hospitalmanagement.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentLoginCreateBinding

class LoginCreateFragment : Fragment() {
    private lateinit var binding: FragmentLoginCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_create, container, false)
        binding.model = this

        return binding.root
    }
}