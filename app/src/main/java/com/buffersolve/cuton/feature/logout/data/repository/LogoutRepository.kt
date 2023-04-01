package com.buffersolve.cuton.feature.logout.data.repository

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.onFailure
import com.buffersolve.cuton.feature.logout.data.remote.api.LogoutService
import com.buffersolve.cuton.feature.logout.data.remote.api.models.LogoutAnswerModel
import com.buffersolve.cuton.core.util.Result
import javax.inject.Inject

class LogoutRepository @Inject constructor(
    private val logoutService: LogoutService,
    private val sessionManager: SessionManager
) {

    suspend fun logout(): NetworkResult<LogoutAnswerModel> {
        val response= logoutService.logout().onFailure { return it }
        return Result.Success(response)
    }

}