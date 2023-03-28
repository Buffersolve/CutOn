package com.buffersolve.cuton.feature.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appInfoManager: AppInfoManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    // Flow
    private val _state = MutableSharedFlow<String>(replay = 1)
    val state: SharedFlow<String> = _state.asSharedFlow()

    fun appVersionValidate() = viewModelScope.launch(Dispatchers.IO) {
        val v = appInfoManager.getVersion()
        val result = authRepository.appVersionValidate(v)
        result.onResult(
            onSuccess = {
                when (it.success.answer) {
                    0 -> _state.emit("You are using the latest version of the application")
                    1 -> _state.emit("There is a newer version of the application")
                    2 -> _state.emit("Your version of the app is out of date. Authorization is not possible")
                }

                Log.d("LoginViewModel", "appVersionValidate: ${it.success.answer}")
            },
            onFailure = {
                Log.d("LoginViewModel", "appVersionValidate: $it")
            }
        )
//        Log.d("LoginViewModel", "appVersionValidate: ${result.onResult({ it.toString() }, { it.toString() })}")
    }



}