package com.buffersolve.cuton.feature.auth.data.remote.api

import android.util.Log
import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginModel
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginResponseModel
import com.buffersolve.cuton.feature.auth.data.remote.api.models.VersionAnswerModel
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class AuthService @Inject constructor(
    @Named("auth") private val retrofit: Retrofit,
    private val sessionManager: SessionManager
) {

    private val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }

    suspend fun appVersionValidate(v: Int): NetworkResult<VersionAnswerModel> {

        val res = authApi.appVersionValidate(v)
//        while (sessionManager.getApiAddress().isEmpty()) {
//            Log.d("AuthService111", "appVersionValidate: waiting for api address")
//            delay(10)
//        }
        return res
    }

    suspend fun login(loginModel: LoginModel): NetworkResult<LoginResponseModel> {

        val login = loginModel.login.toRequestBody("text/plain".toMediaTypeOrNull())
        val password = loginModel.password.toRequestBody("text/plain".toMediaTypeOrNull())
        val devman = loginModel.devman.toRequestBody("text/plain".toMediaTypeOrNull())
        val devmod = loginModel.devmod.toRequestBody("text/plain".toMediaTypeOrNull())
        val devavs = loginModel.devavs.toRequestBody("text/plain".toMediaTypeOrNull())
        val devaid = loginModel.devaid.toRequestBody("text/plain".toMediaTypeOrNull())

        return authApi.login(
            login,
            password,
            devman,
            devmod,
            devavs,
            devaid
        )
    }

}