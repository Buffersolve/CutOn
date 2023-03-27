package com.buffersolve.cuton.feature.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.util.onResult
import com.buffersolve.cuton.feature.auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appInfoManager: AppInfoManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    fun appVersionValidate() = viewModelScope.launch(Dispatchers.IO) {
        val v = appInfoManager.getVersion()
        val result = authRepository.appVersionValidate(v)
        result.onResult(
            onSuccess = {
                Log.d("LoginViewModel", "appVersionValidate: ${it.success.answer}")
            },
            onFailure = {
                Log.d("LoginViewModel", "appVersionValidate: $it")
            }
        )
//        Log.d("LoginViewModel", "appVersionValidate: ${result.onResult({ it.toString() }, { it.toString() })}")
    }



}