package com.example.hospitalmanagement.presentation.util

class HMSActivityUtil(private val activityListener:ActivityListener) {
    fun hideBottomNavigation(hide: Boolean){
        activityListener.hideBottomNavigation(hide)
    }


    interface ActivityListener{
        fun hideBottomNavigation(hide:Boolean)
    }
}