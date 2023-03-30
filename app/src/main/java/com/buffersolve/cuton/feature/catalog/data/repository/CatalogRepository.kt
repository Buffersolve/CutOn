package com.buffersolve.cuton.feature.catalog.data.repository

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.util.onFailure
import com.buffersolve.cuton.feature.catalog.data.remote.api.CatalogService
import com.buffersolve.cuton.feature.catalog.data.remote.api.models.CatalogResponseModel
import com.buffersolve.cuton.core.util.Result
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    private val catalogService: CatalogService
) {

    suspend fun getCatalog() : NetworkResult<CatalogResponseModel> {
        val response = catalogService.getCatalog()
            .onFailure { return it }

        return Result.Success(response)
    }
}