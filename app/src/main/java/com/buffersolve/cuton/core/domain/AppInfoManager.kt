package com.buffersolve.cuton.core.domain

interface AppInfoManager {

    fun saveAppName(appName: String)

    fun saveVersion(v: Int)

    fun getAppName() : String?

    fun getVersion() : Int

}