package com.buffersolve.cuton.core.data.local

import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.SessionManager
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionManager {

    override fun saveApiAddress(route: String): Boolean {
        sharedPreferences.saveApiAddress(route)
        return getApiAddress().isNotEmpty()
    }

    override fun getApiAddress(): String {
        return sharedPreferences.getApiAddress()
    }

    override fun getUserTokenOrNull(): String? {
        return sharedPreferences.getToken()
    }

    override fun saveUserToken(token: String): Boolean {
        sharedPreferences.saveToken(token)
        return !getUserTokenOrNull().isNullOrEmpty()
    }

    override fun clearUserToken(): Boolean {
        return sharedPreferences.clearUserToken()
    }

}