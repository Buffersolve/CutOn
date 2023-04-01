package com.buffersolve.cuton.app.ui.splash.data.repository

import com.buffersolve.cuton.app.ui.splash.data.remote.api.SplashService
import com.buffersolve.cuton.app.ui.splash.data.remote.model.RouteModel
import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.Result
import com.buffersolve.cuton.core.util.onFailure
import kotlinx.coroutines.delay
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val splashService: SplashService,
    private val sessionManager: SessionManager
) {

    suspend fun getRoute(appName: String, v: Int) : NetworkResult<RouteModel> {
        val response = splashService.getRoute(appName, v)
            .onFailure {
                // Delay for 1 second
                delay(1000)

                // Recursive call
                getRoute(appName, v)

                // Return error
                return it
            }

        // Save in SP
        sessionManager.saveApiAddress(response.route)

        return Result.Success(response)
    }


}
