package com.buffersolve.cuton.core.domain

interface SessionManager {

    fun saveRoute(route: String): Boolean

    fun getRoute(): String?

    fun getUserTokenOrNull(): String?

    fun saveUserToken(token: String): Boolean

    fun clearUserToken(): Boolean

}