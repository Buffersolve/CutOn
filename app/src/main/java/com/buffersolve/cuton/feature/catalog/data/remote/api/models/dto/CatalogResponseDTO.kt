package com.buffersolve.cuton.feature.catalog.data.remote.api.models.dto

import com.google.gson.annotations.SerializedName

class CatalogResponseDTO {

    @SerializedName("brands")
    val brandsList: Map<String, CatalogDTO>? = null

}