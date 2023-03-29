package com.buffersolve.cuton.feature.home.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.feature.auth.data.remote.api.AuthApi
import com.buffersolve.cuton.feature.home.data.remote.api.models.UserInfoResponseModel
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class HomeService @Inject constructor(
    @Named("homeRetrofit") private val retrofit: Retrofit,
    private val sessionManager: SessionManager
) {

    private val homeApi: HomeApi by lazy { retrofit.create(HomeApi::class.java) }

    suspend fun getUserInfo(): NetworkResult<UserInfoResponseModel> {

        // Get token from SP
        val token = sessionManager.getUserTokenOrNull()

        if (token.isNullOrEmpty()) {
            throw IllegalStateException("Token is null or empty")
        } else {

            // Request to server
            return homeApi.getUserInfo(token)
        }
    }

}