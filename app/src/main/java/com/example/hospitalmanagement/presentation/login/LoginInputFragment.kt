package com.example.hospitalmanagement.presentation.login

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentLoginInputBinding
import com.example.hospitalmanagement.presentation.MainActivity
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.example.hospitalmanagement.presentation.util.SharePreferenceUtil
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import javax.inject.Inject

@AndroidEntryPoint
class LoginInputFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    @Inject
    lateinit var sharedPrefs: SharePreferenceUtil
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    val actionForgot = Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginForgetPasswordFragment)
    val actionSignUp = Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginCreateFragment)
    private lateinit var binding : FragmentLoginInputBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        database = Firebase.database.reference
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_input, container, false)
        binding.model = this
        binding.loginErrorTv.visibility = View.INVISIBLE

        val emailStream = RxTextView.textChanges(binding.emailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.emailEt.error = if (it) getString(R.string.error_email) else null
        }

        val passwordStream = RxTextView.textChanges(binding.passwordEt)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }

        val invalidFiledStream = Observable.combineLatest(
            emailStream,
            passwordStream
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }
        invalidFiledStream.subscribe { isValid ->
            isEnableSignInButton(isValid)
        }
        binding.btnSignIn.setOnClickListener {
            val email: String = binding.emailEt.text.toString().trim()
            val password: String = binding.passwordEt.text.toString().trim()
            login(email, password)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun login(email: String, password: String) {
        activityUtil.setFullScreenLoading(true)
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(OnSuccessListener<AuthResult> {
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    database = FirebaseDatabase.getInstance().getReference("User").child(uid)
                    database.get().addOnSuccessListener { data ->
                        Log.d("Tag", "onSuccess" + data.getValue())
                        if (data.exists()) {
                            Handler().postDelayed({
                                sharedPrefs.setAuthToken(uid)
                                activity?.let {
                                    startActivity(MainActivity.getLaunchIntent(it))
                                    activityUtil.setFullScreenLoading(false)
                                }
                            }, 3000)
                        }
                        else{
                            activityUtil.setFullScreenLoading(false)
                            binding.loginErrorTv.visibility = View.VISIBLE
                        }
                    }
                })
        }
    }

    private fun isEnableSignInButton(isEnable: Boolean) {
        if (isEnable) {
            binding.btnSignIn.isEnabled = true
            binding.btnSignIn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
        } else {
            binding.btnSignIn.isEnabled = true
            binding.btnSignIn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500_shadow)
        }
    }}
