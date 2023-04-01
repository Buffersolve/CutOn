package com.buffersolve.cuton.app.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.app.ui.splash.data.repository.SplashRepository
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.NetworkConnectivityState
import com.buffersolve.cuton.core.domain.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CutOnViewModel @Inject constructor(
    private val networkConnectivityState: NetworkConnectivityState,
    private val appInfoManager: AppInfoManager,
    private val repository: SplashRepository,
) : ViewModel() {

    // Flow
    private val _networkState = MutableStateFlow(State.Losing)
    val networkState  = _networkState.asStateFlow()

    fun connectivity() = viewModelScope.launch {
        networkConnectivityState.requestNetworkStatus().onEach {
            _networkState.value = it
        }.launchIn(scope = viewModelScope)
    }

    fun saveAppNameAndVersion(appName: String, v: Int) {
        appInfoManager.saveAppName(appName)
        appInfoManager.saveVersion(v)
    }

    fun getApiAddress(appName: String, v: Int) = viewModelScope.launch(Dispatchers.Unconfined) {
        repository.getRoute(appName, v)
    }

}