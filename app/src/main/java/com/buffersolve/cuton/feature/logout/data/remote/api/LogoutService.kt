package com.buffersolve.cuton.feature.logout.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.feature.logout.data.remote.api.models.LogoutAnswerModel
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class LogoutService @Inject constructor(
    @Named("homeRetrofit") private val retrofit: Retrofit,
    private val sessionManager: SessionManager
) {

    private val logoutApi: LogoutApi by lazy { retrofit.create(LogoutApi::class.java) }

    suspend fun logout(): NetworkResult<LogoutAnswerModel> {

        // Get token from SP
        val token = sessionManager.getUserTokenOrNull()

        if (token.isNullOrEmpty()) {
            throw IllegalStateException("Token is null or empty")
        } else {
            // Request to server
            return logoutApi.logout(token)
        }
    }


}