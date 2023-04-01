package com.buffersolve.cuton.feature.logout.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.logout.data.remote.api.models.LogoutAnswerModel
import retrofit2.http.DELETE
import retrofit2.http.Query

interface LogoutApi {

    @DELETE("users/")
    suspend fun logout(

        @Query("token") token: String

    ): NetworkResult<LogoutAnswerModel>

}