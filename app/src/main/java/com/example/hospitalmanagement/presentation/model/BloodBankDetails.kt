package com.example.hospitalmanagement.presentation.model

data class BloodBankDetails(
    var bloodBankId:String? = null,
    var donorName : String = "",
    var bloodGroupType : String = "",
    var donorPhoneNumber : String = "",
    val image:String? = null

)
