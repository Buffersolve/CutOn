package com.buffersolve.cuton.feature.home.data.repository

import com.buffersolve.cuton.core.data.network.result.NetworkResult
import com.buffersolve.cuton.core.util.Result
import com.buffersolve.cuton.core.util.onFailure
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.home.data.remote.api.HomeService
import com.buffersolve.cuton.feature.home.data.remote.api.models.MenuItemResponseModel
import com.buffersolve.cuton.feature.home.data.remote.api.models.UserInfoResponseModel
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {

    suspend fun getUserInfo() : NetworkResult<UserInfoResponseModel> {
        val response = homeService.getUserInfo()
            .onFailure { return it }

        return Result.Success(response)
    }

    suspend fun getHomeMenuItems() : NetworkResult<MenuItemResponseModel> {
        val response = homeService.getHomeMenuItems()
            .onFailure { return it }

        return Result.Success(response)
    }

}