package com.example.hospitalmanagement.presentation.model

data class DoctorInfo(
    var doctorId:String? = null,
    var name : String = "",
    var email : String = "",
    var phoneNumber : String = " ",
    var doctorSpecialization : String = "",
    var doctorDegree : String = "",
    var doctorInstuition : String = "",
    val image:String? = null
)


