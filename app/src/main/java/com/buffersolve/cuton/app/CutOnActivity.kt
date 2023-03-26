package com.buffersolve.cuton.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.buffersolve.cuton.databinding.ActivityCutonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CutOnActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCutonBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Splash Screen
        installSplashScreen()

        // Binding
        binding = ActivityCutonBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        
        
    }

    companion object {
        const val appName: String = "cuton"
        const val v: Int = 36
    }
}