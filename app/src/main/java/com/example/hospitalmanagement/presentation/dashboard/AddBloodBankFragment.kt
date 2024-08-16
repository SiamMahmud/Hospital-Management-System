package com.example.hospitalmanagement.presentation.dashboard

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAddBloodBankBinding
import com.example.hospitalmanagement.presentation.model.BloodBankDetails
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import javax.inject.Inject

@AndroidEntryPoint
class AddBloodBankFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    lateinit var database: DatabaseReference
    private lateinit var binding : FragmentAddBloodBankBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_blood_bank, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        val nameStream = RxTextView.textChanges(binding.dNameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }

        val bloodTypeStream = RxTextView.textChanges(binding.bloodGroupTv)
            .skipInitialValue()
            .map { type ->
                type.isEmpty()
            }

        val contactNumberStream = RxTextView.textChanges(binding.donorContactNumberEt)
            .skipInitialValue()
            .map { number ->
                number.isEmpty()
            }


        val invalidFiledStream = Observable.combineLatest(
            nameStream,
            bloodTypeStream,
            contactNumberStream
        ){nameInvalid:Boolean, bloodTypeInvalid:Boolean,contactNumberInvalid:Boolean ->
            !nameInvalid && !bloodTypeInvalid && !contactNumberInvalid
        }

        invalidFiledStream.subscribe { isValid ->
            isEnableSaveButton(isValid)
        }

        binding.addBldBtnSave.setOnClickListener {
            activityUtil.setFullScreenLoading(true)
            database = FirebaseDatabase.getInstance().getReference("Blood Bank")
            val bloodBankId = database.push().key ?: return@setOnClickListener
            val adminBloodBank = BloodBankDetails(
                bloodBankId = bloodBankId,
                donorName = binding.dNameEt.text.toString().trim(),
                bloodGroupType = binding.bloodGroupTv.text.toString().trim(),
                donorPhoneNumber = binding.donorContactNumberEt.text.toString().trim(),
                image = ""
            )
            database.child(bloodBankId).setValue(adminBloodBank).addOnCompleteListener { task ->
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
            .setMessage("Blood Bank added successfully! Would you like to add more data?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                binding.dNameEt.text?.clear()
                binding.bloodGroupTv.text?.clear()
                binding.donorContactNumberEt.text?.clear()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_addBloodBankFragment_to_admibBloodFragment)
            }
            .create()
            .show()
    }

    private fun isEnableSaveButton(isEnable:Boolean){
        if (isEnable){
            binding.addBldBtnSave.isEnabled = true
            binding.addBldBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.colorPrimary)
        }
        else{
            binding.addBldBtnSave.isEnabled = false
            binding.addBldBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.red_500_shadow)
        }
    }

}