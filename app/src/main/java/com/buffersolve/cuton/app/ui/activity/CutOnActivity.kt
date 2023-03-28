package com.buffersolve.cuton.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buffersolve.cuton.R
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.databinding.ActivityCutonBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class CutOnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCutonBinding
    private lateinit var dialog: AlertDialog

    private val viewModel: CutOnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check Network State
        viewModel.connectivity()

        // Splash Screen
        installSplashScreen().apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.networkState.collect { state ->
                        if (state != State.Available) {
                            dialogWithProgress()
                        } else {
                            if (this@CutOnActivity::dialog.isInitialized) {
                                dialog.dismiss()
                            }
                        }
                    }
                }

            }

            // Save App Name and Version
            viewModel.saveAppNameAndVersion(appName, v)

            // Second api_address
            viewModel.saveSecondApiAddress(appName, v)

        }


        // Binding
        binding = ActivityCutonBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }


    }

    private fun dialogWithProgress(): AlertDialog {
        val dialogView =
            layoutInflater.inflate(R.layout.progress_dialog_layout, binding.root, false)
        val builder = MaterialAlertDialogBuilder(this).apply {
            setView(dialogView)
            setTitle("No internet")
        }
        dialog = builder.show().apply {
            setCancelable(false)
            window?.setLayout(520, 920)
        }

        return dialog
    }


    companion object {
        const val appName: String = "cuton"
        const val v: Int = 36
    }
}