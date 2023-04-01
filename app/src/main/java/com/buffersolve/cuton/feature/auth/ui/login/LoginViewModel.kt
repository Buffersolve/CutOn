package com.buffersolve.cuton.feature.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.app.ui.splash.data.repository.SplashRepository
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginModel
import com.buffersolve.cuton.feature.auth.data.repository.AuthRepository
import com.buffersolve.cuton.feature.auth.ui.login.state.ApiState
import com.buffersolve.cuton.feature.auth.ui.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val networkConnectivityState: NetworkConnectivityState,
    private val appInfoManager: AppInfoManager,
    private val repository: SplashRepository, // TODO change to AuthRepository
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    // Flow
    private val _networkState = MutableStateFlow(State.Unavailable)
    val networkState = _networkState.asStateFlow()

    private val _apiState = MutableSharedFlow<ApiState>(replay = 1)
    val apiState: SharedFlow<ApiState> = _apiState.asSharedFlow()

    private val _versionState = MutableSharedFlow<Int>(replay = 1)
    val versionState: SharedFlow<Int> = _versionState.asSharedFlow()

    private val _loginState = MutableSharedFlow<LoginState>(replay = 1)
    val loginState: SharedFlow<LoginState> = _loginState.asSharedFlow()

    fun getToken(): String? {
        return sessionManager.getUserTokenOrNull()
    }

    fun connectivity() = viewModelScope.launch {
        _networkState.emit(State.Unavailable)
        networkConnectivityState.requestNetworkStatus().onEach {
            _networkState.emit(it)
        }.launchIn(scope = viewModelScope)
    }

//    fun getApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.IO) {
//        repository.getRoute(appName, v).onResult(
//            onSuccess = {
//                sessionManager.saveApiAddress(it.success.route)
//                _apiState.emit(ApiState.Success(true))
//            },
//            onFailure = {
//                _apiState.emit(ApiState.Error(it.cause.message.toString()))
//
//            }
//        )
//    }

    fun getApiFromSP() = viewModelScope.launch {
        val api = sessionManager.getApiAddress()
        _apiState.emit(ApiState.Success(api))
    }

//    fun saveApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.IO) {
//        sessionManager.saveApiAddress(route)
//    }

    fun appVersionValidate() = viewModelScope.launch(Dispatchers.IO) {
        val v = appInfoManager.getVersion()

        authRepository.appVersionValidate(v).onResult(
            onSuccess = {
                _versionState.emit(it.success.answer)
                Log.d("LoginViewModel", "appVersionValidate: ${it.success.answer}")
            },
            onFailure = {
                Log.d("LoginViewModel", "appVersionValidate: $it")
//                authRepository.appVersionValidate(v)
            }
        )

    }

    fun login(loginModel: LoginModel) = viewModelScope.launch(Dispatchers.IO) {
        // Init emit
        _loginState.emit(LoginState.Loading)

        authRepository.login(loginModel).onResult(
            onSuccess = {
                _loginState.emit(LoginState.Success(it.success))
            },
            onFailure = {
                _loginState.emit(LoginState.Error(it.cause.message.toString()))
            }
        )
    }


}