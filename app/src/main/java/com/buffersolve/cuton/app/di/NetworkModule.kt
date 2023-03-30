package com.buffersolve.cuton.app.di

import com.buffersolve.cuton.app.util.Configs.init_api_address
import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.data.network.adapter.ResultAdapterFactory
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return createRetrofit(init_api_address, okHttpClient).build()
    }

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(
        networkConnectivityState: NetworkConnectivityState,
        sessionManager: SessionManager,
        okHttpClient: OkHttpClient
    ): Retrofit {

//        networkConnectivityState.requestNetworkStatus().onEach {  }

        return if (sessionManager.getApiAddress().isNotEmpty()) {
            createRetrofit(sessionManager.getApiAddress(), okHttpClient).build()
        } else {
            createRetrofit(init_api_address, okHttpClient).build()
        }
    }

    @Provides
    @Singleton
    @Named ("homeRetrofit")
    fun provideHomeRetrofit(
        sessionManager: SessionManager,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return if (sessionManager.getApiAddress().isNotEmpty()) {
            createRetrofit(sessionManager.getApiAddress(), okHttpClient).build()
        } else {
            createRetrofit(init_api_address, okHttpClient).build()
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createRetrofit(url: String, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(ResultAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}