package com.example.hospitalmanagement.presentation

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HospitalManagementApplication:Application() {
    companion object{
        @JvmStatic
        fun getApplication(context: Context) = context.applicationContext as HospitalManagementApplication
    }
}