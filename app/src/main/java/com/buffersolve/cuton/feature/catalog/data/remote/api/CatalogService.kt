package com.buffersolve.cuton.feature.catalog.data.remote.api

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.dto.CatalogResponseDTO
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class CatalogService @Inject constructor(
    @Named("catalogRetrofit") private val retrofit: Retrofit,
    private val sessionManager: SessionManager

) {

    private val catalogApi: CatalogApi by lazy { retrofit.create(CatalogApi::class.java) }

    suspend fun getCatalog(): NetworkResult<CatalogResponseDTO> {

        // Get token from SP
        val token = sessionManager.getUserTokenOrNull()

        if (token.isNullOrEmpty()) {
            throw IllegalStateException("Token is null or empty")
        } else {

            // Request to server
            return catalogApi.getCatalog(token)
        }
    }

}