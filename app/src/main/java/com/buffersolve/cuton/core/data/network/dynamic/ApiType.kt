package com.buffersolve.cuton.core.data.network.dynamic

import com.buffersolve.cuton.app.util.Configs.init_api_address
import com.buffersolve.cuton.core.domain.SessionManager
import javax.inject.Inject

sealed class ApiType(
    val url: String
) {

    object Loading : ApiType("")

    object CONNECT : ApiType(init_api_address)

    data class AUTH @Inject constructor(private val sessionManager: SessionManager)
        : ApiType(sessionManager.getApiAddress())


}
