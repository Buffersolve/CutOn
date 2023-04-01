package com.buffersolve.cuton.app.application

import android.app.Application
import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import com.buffersolve.cuton.app.ui.splash.data.repository.SplashRepository
import com.buffersolve.cuton.feature.auth.ui.login.LoginViewModel
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CutOnApp : Application() {

    override fun onCreate() {
        DynamicColors.applyToActivitiesIfAvailable(this)
        super.onCreate()
    }

}