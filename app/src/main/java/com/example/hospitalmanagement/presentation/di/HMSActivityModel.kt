package com.example.hospitalmanagement.presentation.di

import android.app.Activity
import com.example.hospitalmanagement.presentation.util.HMSActivityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HMSActivityModule {

    @Provides
    fun providedMGBActivityUtil(activity: Activity): HMSActivityUtil {
        return HMSActivityUtil(activity as HMSActivityUtil.ActivityListener)
    }
}