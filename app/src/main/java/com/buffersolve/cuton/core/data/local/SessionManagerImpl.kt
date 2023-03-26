package com.buffersolve.cuton.core.data.local

import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.SessionManager
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionManager {

    override fun getUserTokenOrNull(): String? {
        return null
    }

    override fun saveUserToken(token: String): Boolean {
        return true
    }

}