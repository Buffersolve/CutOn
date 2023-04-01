package com.buffersolve.cuton.core.data.local

import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.AppInfoManager
import com.buffersolve.cuton.core.domain.SessionManager
import javax.inject.Inject

class AppInfoManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AppInfoManager {

    // App Name and Version
    override fun saveAppName(appName: String) {
        sharedPreferences.saveAppName(appName)
    }

    override fun saveVersion(v: Int) {
        sharedPreferences.saveVersion(v)
    }

    override fun getAppName(): String? {
        return sharedPreferences.getAppName()
    }

    override fun getVersion(): Int {
        return sharedPreferences.getVersion()
    }

}