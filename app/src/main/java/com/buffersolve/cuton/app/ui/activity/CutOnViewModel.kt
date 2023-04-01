package com.buffersolve.cuton.app.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.app.ui.activity.state.RouteState
import com.buffersolve.cuton.app.ui.splash.data.repository.SplashRepository
import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.auth.ui.login.state.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class CutOnViewModel @Inject constructor(
    private val networkConnectivityState: NetworkConnectivityState,
    private val sessionManager: SessionManager,
    private val appInfoManager: AppInfoManager,
    private val repository: SplashRepository,
) : ViewModel() {

    init {
        connectivity()
    }

    // Flow
//    private val _networkState = MutableStateFlow(State.Unavailable)
//    val networkState: StateFlow<State> = _networkState.asStateFlow(

    private val _networkState = MutableSharedFlow<State>(replay = 1)
    val networkState: SharedFlow<State> = _networkState.asSharedFlow()

    private val _route = MutableSharedFlow<RouteState>(replay = 1)
    val route: SharedFlow<RouteState> = _route.asSharedFlow()

    fun connectivity() = viewModelScope.launch {

//        _networkState.emit(State.Available)

        networkConnectivityState.requestNetworkStatus().onEach {
            Log.d("NetworkState", it.toString())
            _networkState.emit(it)
        }.launchIn(scope = viewModelScope)
    }

    fun saveAppNameAndVersion(appName: String, v: Int) {
        appInfoManager.saveAppName(appName)
        appInfoManager.saveVersion(v)
    }

    fun saveApiToSP(route: String) {
        sessionManager.saveApiAddress(route)
    }

    fun getSecondApiAddress(route: String) = viewModelScope.launch(Dispatchers.IO) {

    }

//    fun saveSecondApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.IO) {
//        repository.getRoute(appName, v).onResult(
//            onSuccess = {
//
////                sessionManager.saveApiAddress(it.success.route)
//            },
//            onFailure = {
//                Log.d("SaveRoute", "Error: $it")
//            }
//        )
//    }

    fun getApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.Unconfined) {
        repository.getRoute(appName, v).onResult(
            onSuccess = {
//                sessionManager.saveApiAddress(it.success.route)

                _route.emit(RouteState.Success(it.success.route))
            },
            onFailure = {
                _route.emit(RouteState.Error(it.cause.message.toString()))

            }
        )
    }


}