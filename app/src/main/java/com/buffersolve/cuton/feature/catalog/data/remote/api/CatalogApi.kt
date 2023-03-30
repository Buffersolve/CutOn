package com.buffersolve.cuton.feature.catalog.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.dto.CatalogResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogApi {

    @GET("catalog/brands/")
    suspend fun getCatalog(

        @Query("token")
        token: String,

        ): NetworkResult<CatalogResponseDTO>

}