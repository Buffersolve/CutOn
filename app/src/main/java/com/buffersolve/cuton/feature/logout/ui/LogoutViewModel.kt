package com.buffersolve.cuton.feature.logout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.logout.data.repository.LogoutRepository
import com.buffersolve.cuton.feature.logout.ui.state.LogoutState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val repository: LogoutRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _logoutState = MutableSharedFlow<LogoutState>(replay = 1)
    val logoutState: SharedFlow<LogoutState> = _logoutState.asSharedFlow()

    fun deleteToken() {
        sessionManager.clearUserToken()
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        repository.logout().onResult(
            onSuccess = {
//                sessionManager.clearUserToken()
                _logoutState.emit(LogoutState.Success(it.success))
            },
            onFailure = {
                _logoutState.emit(LogoutState.Error(it.cause.message ?: "Unknown error"))
            }

        )
    }

}