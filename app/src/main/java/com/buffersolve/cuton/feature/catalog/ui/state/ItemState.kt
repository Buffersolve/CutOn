package com.buffersolve.cuton.feature.catalog.ui.state

import com.buffersolve.cuton.feature.catalog.data.remote.api.models.X10

sealed class HomeState {
    object Loading : HomeState()

    data class Success(val answer: List<X10>) : HomeState()

    data class Error(val error: String) : HomeState()
}
