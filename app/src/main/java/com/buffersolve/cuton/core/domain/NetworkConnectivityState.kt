package com.buffersolve.cuton.core.domain

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityState {

    suspend fun requestNetworkStatus(): Flow<State>

}

enum class State {
    Available,
    Unavailable,
    Losing,
    Lost,
}
