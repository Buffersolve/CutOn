package com.buffersolve.cuton.feature.auth.data.repository

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.onFailure
import com.buffersolve.cuton.feature.auth.data.remote.api.AuthService
import com.buffersolve.cuton.feature.auth.data.remote.api.models.VersionAnswerModel
import com.buffersolve.cuton.core.util.Result
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginModel
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) {

    suspend fun appVersionValidate(v: Int): NetworkResult<VersionAnswerModel> {
        val versionAnswer = authService.appVersionValidate(v)
            .onFailure { return it }

        return Result.Success(versionAnswer)
    }

    suspend fun login(loginModel: LoginModel): NetworkResult<Unit> {
        val loginResponse = authService.login(loginModel)
            .onFailure { return it }

        // saveToken
        sessionManager.saveUserToken(loginResponse.token)

        return Result.Success(Unit)
    }


}