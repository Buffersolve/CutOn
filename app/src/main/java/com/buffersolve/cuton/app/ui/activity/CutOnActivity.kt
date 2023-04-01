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
import com.buffersolve.cuton.app.ui.activity.state.RouteState
import com.buffersolve.cuton.app.util.Configs.appName
import com.buffersolve.cuton.app.util.Configs.v
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.databinding.ActivityCutonBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CutOnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCutonBinding
    private lateinit var dialog: AlertDialog

    private val viewModel: CutOnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Save App Name and Version
        viewModel.saveAppNameAndVersion(appName, v)

//        // Second init_api_address
//        viewModel.saveSecondApiAddress(appName, v)

        // init_api_address
//        viewModel.getApiAddress(appName, v)


        // Check Network State
//        viewModel.connectivity()

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

                            viewModel.getApiAddress(appName, v)

                            viewModel.route.collect {
                                when (it) {
                                    is RouteState.Success -> {
                                        // Login
                                        viewModel.saveApiToSP(it.response)
                                    }
                                    is RouteState.Error -> {
                                        // Main
                                    }
                                    is RouteState.Loading -> {
                                        // Loading
                                    }
                                }
                            }
                        }
                    }
                }
            }


//            // Save App Name and Version
//            viewModel.saveAppNameAndVersion(appName, v)
//
//            // Second init_api_address
//            viewModel.saveSecondApiAddress(appName, v)

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

}