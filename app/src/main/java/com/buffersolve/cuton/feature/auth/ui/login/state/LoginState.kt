package com.buffersolve.cuton.feature.auth.ui.login.state

sealed class LoginState {

    object Loading: LoginState()

    data class Success(val response: Unit) : LoginState()

    data class Error(val error: String) : LoginState()

}