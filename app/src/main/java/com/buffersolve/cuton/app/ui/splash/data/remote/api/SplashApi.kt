package com.buffersolve.cuton.app.ui.splash.data.remote.api

import com.buffersolve.cuton.app.ui.splash.data.remote.model.RouteModel
import com.buffersolve.cuton.core.data.network.result.NetworkResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SplashApi {

    @GET("routes")
    suspend fun getRoute(

        @Query("appName") appName: String,
        @Query("v") v: Int,

        ): NetworkResult<RouteModel>

}