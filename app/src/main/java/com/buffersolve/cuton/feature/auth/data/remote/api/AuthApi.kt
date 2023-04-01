package com.buffersolve.cuton.feature.auth.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginResponseModel
import com.buffersolve.cuton.feature.auth.data.remote.api.models.VersionAnswerModel
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApi {

    @GET("/app/version/latest/")
    suspend fun appVersionValidate(

        @Query("v") v: Int,

        ): NetworkResult<VersionAnswerModel>

    @Multipart
    @POST("/users/login/")
    suspend fun login(

        @Part("login") login: RequestBody,
        @Part("password") password: RequestBody,
        @Part("devman") devman: RequestBody,
        @Part("devmod") devmod: RequestBody,
        @Part("devavs") devavs: RequestBody,
        @Part("devaid") devaid: RequestBody,

        ): NetworkResult<LoginResponseModel>

}