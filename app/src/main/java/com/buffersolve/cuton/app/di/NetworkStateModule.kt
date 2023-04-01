package com.buffersolve.cuton.app.di

import android.content.Context
import com.buffersolve.cuton.core.data.network.connectivity.NetworkConnectivityStateImpl
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkStateModule {

    @Provides
    @Singleton
    fun provideNetworkState(@ApplicationContext context: Context): NetworkConnectivityState {
        return NetworkConnectivityStateImpl(context)
    }

}