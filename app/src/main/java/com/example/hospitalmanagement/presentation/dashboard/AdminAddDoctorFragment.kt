package com.example.hospitalmanagement.presentation.dashboard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminAddDoctorBinding
import com.example.hospitalmanagement.databinding.FragmentAdminViewDoctorListBinding
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminAddDoctorFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    private lateinit var binding: FragmentAdminAddDoctorBinding
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    @SuppressLint("CheckResult", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_doctor, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        val nameStream = RxTextView.textChanges(binding.dNameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            binding.dNameEt.error = if (it) getString(R.string.error_name) else null
        }

        val emailStream = RxTextView.textChanges(binding.doctorEmailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.doctorEmailEt.error = if (it) getString(R.string.error_email) else null
        }

        val phoneNumberStream = RxTextView.textChanges(binding.doctorPhoneNumberEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        phoneNumberStream.subscribe {
            binding.doctorPhoneNumberEt.error = if (it) getString(R.string.error_number) else null
        }

        val doctorSpecializationStream = RxTextView.textChanges(binding.dSpecializationEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        doctorSpecializationStream.subscribe {
            binding.dSpecializationEt.error = if (it) getString(R.string.error_number) else null
        }

        val doctorDegreeStream = RxTextView.textChanges(binding.doctorDegreeEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        doctorDegreeStream.subscribe {
            binding.dSpecializationEt.error = if (it) getString(R.string.error_number) else null
        }

        val doctorInstuitionStream = RxTextView.textChanges(binding.doctorInstuitionEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        doctorInstuitionStream.subscribe {
            binding.dSpecializationEt.error = if (it) getString(R.string.error_number) else null
        }

        val invalidFiledStream = io.reactivex.Observable.combineLatest(
            nameStream,
            emailStream,
            phoneNumberStream,
            doctorSpecializationStream,
            doctorDegreeStream,
            doctorInstuitionStream
        ) { nameInvalid: Boolean, emailInvalid: Boolean, phoneInvalid: Boolean, doctorSpecializationInvalid : Boolean, doctorDegreeInvalid : Boolean, doctorInstuitionInvalid : Boolean->
            !nameInvalid && !emailInvalid && !phoneInvalid && !doctorSpecializationInvalid && !doctorDegreeInvalid && !doctorInstuitionInvalid }

        invalidFiledStream.subscribe { isValid ->
            isEnableSignUpButton(isValid)
        }

        binding.btnSave.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("Doctor")
            val doctorId = database.push().key ?: return@setOnClickListener
            val doctor = DoctorInfo(
                doctorId = doctorId,
                name = binding.dNameEt.text.toString().trim(),
                email = binding.doctorEmailEt.text.toString().trim(),
                phoneNumber = binding.doctorPhoneNumberEt.text.toString().trim(),
                doctorSpecialization = binding.dSpecializationEt.text.toString().trim(),
                doctorDegree = binding.doctorDegreeEt.text.toString().trim(),
                doctorInstuition = binding.doctorInstuitionEt.text.toString().trim(),
                image = ""
            )
            database.child(doctorId).setValue(doctor).addOnCompleteListener { task ->
                activityUtil.setFullScreenLoading(false)
                if (task.isSuccessful) {
                    showSuccessDialog()
                } else {
                    Toast.makeText(requireContext(), "Failed to add entry. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Success")
            .setMessage("Doctor added successfully! Would you like to add more doctors?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                binding.dNameEt.text?.clear()
                binding.doctorEmailEt.text?.clear()
                binding.doctorPhoneNumberEt.text?.clear()
                binding.dSpecializationEt.text?.clear()
                binding.doctorDegreeEt.text?.clear()
                binding.doctorInstuitionEt.text?.clear()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_adminAddDoctorFragment_to_adminViewDoctorListFragment)
            }
            .create()
            .show()
    }

    private fun isEnableSignUpButton(isEnable: Boolean) {
        if (isEnable == true) {
            binding.btnSave.isEnabled = true
            binding.btnSave.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
        } else {
            binding.btnSave.isEnabled = false
            binding.btnSave.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500_shadow)
        }
    }
}


