package com.buffersolve.cuton.app.ui.splash.data.repository

import com.buffersolve.cuton.app.ui.splash.data.remote.api.SplashService
import com.buffersolve.cuton.app.ui.splash.data.remote.model.RouteModel
import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.Result
import com.buffersolve.cuton.core.util.onFailure
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val splashService: SplashService,
    private val sessionManager: SessionManager
) {

    suspend fun getRoute(appName: String, v: Int) : NetworkResult<RouteModel> {
        val response = splashService.getRoute(appName, v)
            .onFailure { return it }

        // Save in SP
        sessionManager.saveApiAddress(response.route)
//        Log.d("SaveRoute", "Route: ${sessionManager.getRoute()}")

        return Result.Success(response)
    }

    fun saveRoute(route: String) : Boolean {
        sessionManager.saveApiAddress(route)
        return true
    }

}
