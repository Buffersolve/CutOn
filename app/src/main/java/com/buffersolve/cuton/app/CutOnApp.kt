package com.buffersolve.cuton.app

import android.app.Application
import com.google.android.material.color.DynamicColors

class CutOnApp : Application() {

    override fun onCreate() {
        DynamicColors.applyToActivitiesIfAvailable(this)
        super.onCreate()
    }

}