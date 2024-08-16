package com.example.hospitalmanagement.presentation.model

data class AmbulanceDetails (
    var ambulanceId:String? = null,
    var driverName : String = "",
    var ambulLocation : String ="",
    var licenceNumber : String = "",
    var phoneNumber : String = "",
    val image:String? = null

)