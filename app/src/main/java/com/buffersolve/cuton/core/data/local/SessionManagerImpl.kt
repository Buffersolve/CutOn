package com.buffersolve.cuton.core.data.local

import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.SessionManager
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionManager {
    override fun saveRoute(route: String): Boolean {
        sharedPreferences.saveRoute(route)
        return !getUserTokenOrNull().isNullOrEmpty()
    }

    override fun getRoute(): String? {
        return sharedPreferences.getRoute()
    }

    override fun getUserTokenOrNull(): String? {
        return sharedPreferences.getToken()
    }

    override fun saveUserToken(token: String): Boolean {
        sharedPreferences.saveToken(token)
        return !getUserTokenOrNull().isNullOrEmpty()
    }

    override fun clearUserToken(): Boolean {
        TODO("Not yet implemented")
    }

}