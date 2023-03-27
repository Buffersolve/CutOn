package com.buffersolve.cuton.app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CutOnViewModel @Inject constructor(
    private val networkConnectivityState: NetworkConnectivityState,
    private val sessionManager: SessionManager,
    private val appInfoManager: AppInfoManager,
) : ViewModel() {

    // Flow
//    private val _networkState = MutableSharedFlow<State>(replay = 1)
//    val networkState: SharedFlow<State> = _networkState.asSharedFlow()
    private val _networkState = MutableStateFlow(State.Unavailable)
    val networkState: StateFlow<State> = _networkState.asStateFlow()

    fun connectivity() = viewModelScope.launch {
        networkConnectivityState.requestNetworkStatus().onEach {
//            _networkState.emit(it)
            _networkState.value = it
            Log.wtf("Connectivity111", it.toString())
            Log.wtf("Connectivity111", networkState.value.toString())
        }.launchIn(scope = viewModelScope)
    }

    fun saveAppNameAndVersion(appName: String, v: Int) {
        appInfoManager.saveAppName(appName)
        appInfoManager.saveVersion(v)
        Log.d("SaveAppNameAndVersion", "AppName: ${appInfoManager.getAppName()}")
        Log.d("SaveAppNameAndVersion", "AppName: ${appInfoManager.getVersion()}")
    }

}