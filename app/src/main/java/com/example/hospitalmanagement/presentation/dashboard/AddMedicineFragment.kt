package com.example.hospitalmanagement.presentation.dashboard

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.databinding.FragmentAddMedicineBinding
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddMedicineFragment : Fragment() {
    @Inject
    lateinit var activityUtil: HMSActivityUtil
    lateinit var database: DatabaseReference
    private lateinit var binding : FragmentAddMedicineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_medicine, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        database = Firebase.database.reference
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        val nameStream = RxTextView.textChanges(binding.nameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }

        val companyStream = RxTextView.textChanges(binding.companyEt)
            .skipInitialValue()
            .map { company ->
                company.isEmpty()
            }

        val detailsStream = RxTextView.textChanges(binding.detailsEt)
            .skipInitialValue()
            .map { details ->
                details.isEmpty()
            }

        val priceStream = RxTextView.textChanges(binding.priceEt)
            .skipInitialValue()
            .map { price ->
                price.isEmpty()
            }

        val expireDateStream = RxTextView.textChanges(binding.expireDateEt)
            .skipInitialValue()
            .map { expire ->
                expire.isEmpty()
            }

        binding.expireDateEt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val datePickerDialog = DatePickerDialog(requireContext())
                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                    val formattedDate = dateFormat.format(selectedDate.time)
                    binding.expireDateEt.setText(formattedDate)
                }
                datePickerDialog.show()
                return@OnTouchListener true
            }
            false
        })
        val selfStream = RxTextView.textChanges(binding.selfEt)
            .skipInitialValue()
            .map { self ->
                self.isEmpty()
            }

        val rowStream = RxTextView.textChanges(binding.rowEt)
            .skipInitialValue()
            .map { row ->
                row.isEmpty()
            }

        val columnStream = RxTextView.textChanges(binding.columnEt)
            .skipInitialValue()
            .map { column ->
                column.isEmpty()
            }

        val invalidFiledStream = Observable.combineLatest(
            nameStream,
            companyStream,
            detailsStream,
            priceStream,
            expireDateStream,
            selfStream,
            rowStream,
            columnStream
        ){nameInvalid:Boolean, companyInvalid:Boolean,detailsInvalid:Boolean,priceInvalid:Boolean, expiredateInvalid:Boolean, selfInvalid:Boolean,rowInvalid:Boolean,columnInvalid:Boolean ->
            !nameInvalid && !columnInvalid && !detailsInvalid && !priceInvalid && !expiredateInvalid && !selfInvalid && !rowInvalid && !columnInvalid
        }
        invalidFiledStream.subscribe { isValid ->
            isEnableSaveButton(isValid)
        }

        binding.addMediBtnSave.setOnClickListener {
            activityUtil.setFullScreenLoading(true)
            val medicineId = database.push().key!!
            val medicine = HashMap<String,Any>()
            medicine["name"] = binding.nameEt.text!!.toString().trim()
            medicine["company"] = binding.companyEt.text!!.toString().trim()
            medicine["details"] = binding.detailsEt.text!!.toString().trim()
            medicine["price"] = binding.priceEt.text!!.toString().trim()
            medicine["date"] = binding.expireDateEt.text!!.toString().trim()
            medicine["self"] = binding.selfEt.text!!.toString().trim()
            medicine["row"] = binding.rowEt.text!!.toString().trim()
            medicine["column"] = binding.columnEt.text!!.toString().trim()
            database.child("Medicine").child(medicineId).setValue(medicine).addOnCompleteListener { task ->
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
            .setMessage("Medicine added successfully! Would you like to add more data?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                binding.nameEt.text!!.clear()
                binding.companyEt.text!!.clear()
                binding.detailsEt.text!!.clear()
                binding.priceEt.text!!.clear()
                binding.expireDateEt.text!!.clear()
                binding.selfEt.text!!.clear()
                binding.rowEt.text!!.clear()
                binding.columnEt.text!!.clear()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(R.id.action_addMedicineFragment_to_medicineListFragment)
            }
            .create()
            .show()
    }

    private fun isEnableSaveButton(isEnable:Boolean){
        if (isEnable){
            binding.addMediBtnSave.isEnabled = true
            binding.addMediBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.colorPrimary)
        }
        else{
            binding.addMediBtnSave.isEnabled = false
            binding.addMediBtnSave.backgroundTintList = ContextCompat.getColorStateList(requireContext(),R.color.red_500_shadow)
        }
    }

}