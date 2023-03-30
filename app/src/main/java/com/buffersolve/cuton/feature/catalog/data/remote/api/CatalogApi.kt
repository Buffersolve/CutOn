package com.buffersolve.cuton.feature.catalog.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.CatalogResponseModel
import com.google.gson.reflect.TypeToken
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogApi {

    @GET("catalog/brands/")
    suspend fun getCatalog(

        @Query("token")
        token: String,

        ): NetworkResult<CatalogResponseModel>

}