package com.buffersolve.cuton.app.ui.activity.state

sealed class RouteState {

    object Loading: RouteState()

    data class Success(val response: String) : RouteState()

    data class Error(val error: String) : RouteState()

}