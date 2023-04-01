package com.buffersolve.cuton.feature.logout.ui.state

import com.buffersolve.cuton.feature.logout.data.remote.api.models.LogoutAnswerModel

sealed class LogoutState {

    object Loading : LogoutState()

    data class Success(val answer: LogoutAnswerModel) : LogoutState()

    data class Error(val error: String) : LogoutState()

}
