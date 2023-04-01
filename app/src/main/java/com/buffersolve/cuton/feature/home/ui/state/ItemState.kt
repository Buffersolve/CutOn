package com.buffersolve.cuton.feature.home.ui.state

import com.buffersolve.cuton.feature.home.data.remote.api.models.MenuItemResponseModel

sealed class ItemState {
    object Loading : ItemState()

    data class Success(val answer: MenuItemResponseModel) : ItemState()

    data class Error(val error: String) : ItemState()
}
