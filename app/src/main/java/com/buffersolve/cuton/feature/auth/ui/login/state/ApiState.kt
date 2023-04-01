package com.buffersolve.cuton.feature.auth.ui.login.state

sealed class ApiState {

    object Loading: ApiState()

    data class Success(val response: String) : ApiState()

    data class Error(val error: String) : ApiState()

}