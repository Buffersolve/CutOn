package com.buffersolve.cuton.app.di

import com.buffersolve.cuton.core.data.local.AppInfoManagerImpl
import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.AppInfoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppInfoModule {

    @Provides
    @Singleton
    fun provideAppInfoManager(sharedPreferences: SharedPreferences): AppInfoManager {
        return AppInfoManagerImpl(sharedPreferences)
    }

}