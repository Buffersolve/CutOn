package com.buffersolve.cuton.feature.auth.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.auth.data.remote.api.models.VersionAnswerModel
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class AuthService @Inject constructor(
    private val retrofit: Retrofit
) {

    private val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }

    suspend fun appVersionValidate(v: Int): NetworkResult<VersionAnswerModel> {
        return authApi.appVersionValidate(v)
    }

//    suspend fun login(phone: String, password: String): NetworkResult<LoginResponse> {
//        val loginModel = LoginModel(phone, password)
//        return authApi.login(loginModel)
//    }

}