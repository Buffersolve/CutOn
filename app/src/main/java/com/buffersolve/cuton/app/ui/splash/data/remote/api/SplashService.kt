package com.buffersolve.cuton.app.ui.splash.data.remote.api

import com.buffersolve.cuton.app.ui.splash.data.remote.model.RouteModel
import com.buffersolve.cuton.core.data.network.result.NetworkResult
import retrofit2.Retrofit
import javax.inject.Inject

class SplashService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val splashApi: SplashApi by lazy {
        retrofit.create(SplashApi::class.java)
    }

    suspend fun getRoute(appName: String, v: Int) : NetworkResult<RouteModel> {
        return splashApi.getRoute(appName, v)
    }

}