package com.example.hospitalmanagement.presentation.di

import com.example.hospitalmanagement.presentation.util.ISharedPreferencesUtil
import com.example.hospitalmanagement.presentation.util.SharePreferenceUtil
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
interface OtherInterfacesModule {

    @Binds
    fun bindSharePreferencesUtil(sharePreferencesUtil: SharePreferenceUtil): ISharedPreferencesUtil
}