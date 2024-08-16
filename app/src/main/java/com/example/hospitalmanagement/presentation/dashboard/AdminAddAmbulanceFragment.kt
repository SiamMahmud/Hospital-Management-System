package com.example.hospitalmanagement.presentation.dashboard
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAdminAddAmbulanceBinding
import com.example.hospitalmanagement.presentation.model.AmbulanceDetails
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import javax.inject.Inject

@AndroidEntryPoint
class AdminAddAmbulanceFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    lateinit var database: DatabaseReference
    private lateinit var binding: FragmentAdminAddAmbulanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_ambulance, container, false)
        binding.model = this
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        val nameStream = RxTextView.textChanges(binding.ambulanceDriverNameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }

        val phoneNumberStream = RxTextView.textChanges(binding.driverPhoneNumberEt)
            .skipInitialValue()
            .map { number ->
                number.isEmpty()
            }

        val ambulanceLocationStream = RxTextView.textChanges(binding.ambulanceLocationEt)
            .skipInitialValue()
            .map { location ->
                location.isEmpty()
            }

        val ambulanceNumberPlatStream = RxTextView.textChanges(binding.ambulanceNumberPlatEt)
            .skipInitialValue()
            .map { numberPlat ->
                numberPlat.isEmpty()
            }

        val invalidFiledStream = Observable.combineLatest(
            nameStream,
            phoneNumberStream,
            ambulanceLocationStream,
            ambulanceNumberPlatStream

        ){nameInvalid:Boolean, phoneNumberInvalid:Boolean,ambulanceLocationInvalid:Boolean, ambulanceNumberPlatInvalid:Boolean->
            !nameInvalid && !phoneNumberInvalid && !ambulanceLocationInvalid && !ambulanceNumberPlatInvalid
        }

        invalidFiledStream.subscribe { isValid ->
            isEnableSaveButton(isValid)
        }

        binding.ambulanceBtnSave.setOnClickListener {
            activityUtil.setFullScreenLoading(true)
            database = FirebaseDatabase.getInstance().getReference("Ambulance")
            val ambulanceId = database.push().key ?: return@setOnClickListener
            val adminAmbulance = AmbulanceDetails(
                ambulanceId = ambulanceId,
                driverName = binding.ambulanceDriverNameEt.text.toString().trim(),
                phoneNumber = binding.driverPhoneNumberEt.text.toString().trim(),
                licenceNumber = binding.ambulanceLocationEt.text.toString().trim(),
                ambulLocation = binding.ambulanceNumberPlatEt.text.toString().trim(),
                image = ""
            )
            database.child(ambulanceId).setValue(adminAmbulance).addOnCompleteListener { task ->
                activityUtil.setFullScreenLoading(false)
                if (task.isSuccessful) {
                    showSuccessDialog()
                } else {
                    // Handle failure if needed
                }
            }
        }

        return binding.root
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Success")
            .setMessage("Ambulance added successfully! Would you like to add more ambulances?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                binding.ambulanceDriverNameEt.text?.clear()
                binding.driverPhoneNumberEt.text?.clear()
                binding.ambulanceLocationEt.text?.clear()
                binding.ambulanceNumberPlatEt.text?.clear()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_adminAddAmbulanceFragment_to_ambulanceFragment)
            }
            .create()
            .show()
    }

    private fun isEnableSaveButton(isEnable:Boolean){
        if (isEnable){
            binding.ambulanceBtnSave.isEnabled = true
            binding.ambulanceBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.colorPrimary)
        }
        else{
            binding.ambulanceBtnSave.isEnabled = false
            binding.ambulanceBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.red_500_shadow)
        }
    }
}