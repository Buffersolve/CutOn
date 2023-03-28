package com.buffersolve.cuton.core.data.local.sharedpref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.JsonReader
import com.buffersolve.cuton.app.util.Configs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.StringReader
import javax.inject.Inject

class SharedPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
    private val gson = Gson()

    // Route
    fun saveApiAddress(route: String) {
        sharedPreferences.edit()
            .putString(api_address, route)
            .apply()
    }

    fun getApiAddress(): String {
        return sharedPreferences.getString(api_address, null) ?: ""
    }

    // Token
    fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, gson.toJson(token))
            .apply()
    }

    fun getToken(): String? {
        return gson.fromJson(
            sharedPreferences.getString(TOKEN_KEY, null),
            String::class.java
        )
    }

    fun clearUserToken(): Boolean {
        TODO("Not yet implemented")
    }

    /*
    App Name and Version
    */

    fun saveAppName(appName: String) {
        sharedPreferences.edit()
            .putString(APP_NAME, appName)
            .apply()
    }

    fun saveVersion(v: Int) {
        sharedPreferences.edit()
            .putInt(APP_VERSION, v)
            .apply()
    }

    fun getAppName(): String? {
        return sharedPreferences.getString(APP_NAME, null)
    }

    fun getVersion(): Int {
        return sharedPreferences.getInt(APP_VERSION, 0)

    }

    companion object {
        private const val api_address = "api_address"

        private const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        private const val TOKEN_KEY = "TOKEN_KEY"

        private const val APP_NAME = "APP_NAME"
        private const val APP_VERSION = "APP_VERSION"
    }

}