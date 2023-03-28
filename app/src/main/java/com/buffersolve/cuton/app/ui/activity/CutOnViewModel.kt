package com.buffersolve.cuton.app.ui.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.app.ui.splash.data.repository.SplashRepository
import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.core.util.onResult
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

    // Flow
    private val _networkState = MutableStateFlow(State.Unavailable)
    val networkState: StateFlow<State> = _networkState.asStateFlow()

    fun connectivity() = viewModelScope.launch {
        networkConnectivityState.requestNetworkStatus().onEach {
            _networkState.value = it
        }.launchIn(scope = viewModelScope)
    }

    fun saveAppNameAndVersion(appName: String, v: Int) {
        appInfoManager.saveAppName(appName)
        appInfoManager.saveVersion(v)
    }

    fun saveSecondApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getRoute(appName, v).onResult(
            onSuccess = {
                sessionManager.saveApiAddress(it.success.route)
            },
            onFailure = {
                Log.d("SaveRoute", "Error: $it")
            }
        )
    }


}