package com.buffersolve.cuton.feature.catalog.data.remote.api.models.dto

import com.google.gson.annotations.SerializedName


data class CatalogDTO(

    @SerializedName("brandId")
    val brandId: Int? = null,

    @SerializedName("brandName")
    val brandName: String? = null,

    @SerializedName("brandImage")
    val brandImage: String? = null
)
