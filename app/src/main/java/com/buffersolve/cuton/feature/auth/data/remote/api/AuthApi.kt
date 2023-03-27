package com.buffersolve.cuton.feature.auth.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.auth.data.remote.api.models.VersionAnswerModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("/app/version/latest/")
    suspend fun appVersionValidate(

        @Query("v") v: Int,

    ): NetworkResult<VersionAnswerModel>

}