package com.buffersolve.cuton.core.domain

interface SessionManager {

    fun saveApiAddress(route: String): Boolean

    fun getApiAddress(): String

    fun getUserTokenOrNull(): String?

    fun saveUserToken(token: String): Boolean

    fun clearUserToken(): Boolean

}