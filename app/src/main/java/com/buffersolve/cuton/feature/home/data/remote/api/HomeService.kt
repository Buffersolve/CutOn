package com.buffersolve.cuton.feature.home.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.feature.auth.data.remote.api.AuthApi
import com.buffersolve.cuton.feature.home.data.remote.api.models.MenuItemResponseModel
import com.buffersolve.cuton.feature.home.data.remote.api.models.UserInfoResponseModel
import com.buffersolve.cuton.core.util.Result
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class HomeService @Inject constructor(
    @Named("homeRetrofit") private val retrofit: Retrofit,
    private val sessionManager: SessionManager
) {

    private val homeApi: HomeApi by lazy { retrofit.create(HomeApi::class.java) }

    suspend fun getUserInfo(): NetworkResult<UserInfoResponseModel>? {

        // Get token from SP
        val token = sessionManager.getUserTokenOrNull()

//        if (token.isNullOrEmpty()) {
//            throw IllegalStateException("Token is null or empty")
//            return com.buffersolve.cuton.core.util.Result.Failure("Token is null or empty")
//            return Result.Failure("Token is null or empty")
//        } else {
            // Request to server
            return token?.let { homeApi.getUserInfo(it) }
//        }
    }

    suspend fun getHomeMenuItems(): NetworkResult<MenuItemResponseModel>? {

        // Get token from SP
        val token = sessionManager.getUserTokenOrNull()

//        if (token.isNullOrEmpty()) {
//            throw IllegalStateException("Token is null or empty")
//        } else {

            // Request to server
            return token?.let { homeApi.getHomeMenuItems(it) }
//        }
    }

}