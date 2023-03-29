package com.buffersolve.cuton.feature.home.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.home.data.remote.api.models.UserInfoResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("/users/")
    suspend fun getUserInfo(

        @Query ("token")
        token: String

    ): NetworkResult<UserInfoResponseModel>

}