package com.example.hospitalmanagement.presentation.util

interface ISharedPreferencesUtil {
    fun logout()
    fun getAuthToken():String?
    fun setAuthToken(token:String)
}