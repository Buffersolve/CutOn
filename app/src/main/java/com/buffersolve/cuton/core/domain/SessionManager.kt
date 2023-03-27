package com.buffersolve.cuton.core.domain

interface SessionManager {

    fun getUserTokenOrNull(): String?

    fun saveUserToken(token: String): Boolean

    fun clearUserToken(): Boolean

}