package com.buffersolve.cuton.feature.auth.ui.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.buffersolve.cuton.R
import com.buffersolve.cuton.app.util.Configs.appName
import com.buffersolve.cuton.app.util.Configs.v
import com.buffersolve.cuton.core.data.local.SessionManagerImpl
import com.buffersolve.cuton.core.data.local.sharedpref.SharedPreferences
import com.buffersolve.cuton.core.domain.SessionManager
import com.buffersolve.cuton.core.domain.State
import com.buffersolve.cuton.databinding.FragmentLoginBinding
import com.buffersolve.cuton.feature.auth.data.remote.api.models.LoginModel
import com.buffersolve.cuton.feature.auth.ui.login.state.ApiState
import com.buffersolve.cuton.feature.auth.ui.login.state.LoginState
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check token
        checkToken()

        // Check Network State
        viewModel.connectivity()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.networkState.collect { state ->
                    when (state) {
                        State.Available -> {



//                            viewModel.getApiAddress(appName, v)
//                            viewModel.

                        }
                        else -> {
                            // nth
                        }

                    }

                }

            }
        }

        viewModel.getApiFromSP()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.apiState.collect { apiState ->
                    when (apiState) {
                        is ApiState.Success -> {

                            // Save init_api_address
                            viewModel.appVersionValidate()

                        }
                        else -> {
                            // nth
//                            viewModel.appVersionValidate()
                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.versionState.collect { state ->

                    // Hide progress
                    with(binding) {
                        progressAppVersion.visibility = View.GONE
                        tvAppVersion.visibility = View.VISIBLE
                    }

                    when (state) {
                        0 -> binding.tvAppVersion.text = getString(R.string.version_answer_0)
                        1 -> binding.tvAppVersion.text = getString(R.string.version_answer_1)
                        2 -> {
                            with(binding) {
                                tvAppVersion.text = getString(R.string.version_answer_2)
                                btnLogin.isEnabled = false
                            }
                        }
                    }
                }
            }
        }

        // Login
        binding.btnLogin.setOnClickListener {
            val login = binding.tiePhone.text.toString()
            val password = binding.tiePass.text.toString()
            val loginModel = LoginModel(login, password, devman, devmod, devavs, devaid)

            // Request
            viewModel.login(loginModel)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Error -> {

                            // Snack bar Error
                            snackBarError(state.error)

                            with(binding) {
                                tiPhone.isErrorEnabled = true
                                tiPass.isErrorEnabled = true
                                tiPhone.error = " "
                                tiPass.error = " "
                                tiPass.errorIconDrawable = null
                            }

                            dialog.dismiss()

                        }
                        is LoginState.Success -> {
                            dialog.dismiss()

                            with(binding) {
                                tiPhone.isErrorEnabled = false
                                tiPass.isErrorEnabled = false
                            }

                            // Navigate to HomeFragment
                            val navController = Navigation.findNavController(view)
                            navController.navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                        is LoginState.Loading -> {
                            dialogWithProgress()
                        }
                    }
                }
            }
        }


    }

//    override fun onStart() {
//        super.onStart()
//        checkToken()
//    }

    private fun dialogWithProgress(): AlertDialog {
        val dialogView =
            layoutInflater.inflate(R.layout.progress_dialog_layout, binding.root, false)
        val builder = MaterialAlertDialogBuilder(requireContext()).apply {
            setView(dialogView)
        }
        dialog = builder.show().apply {
            setCancelable(false)
            window?.setLayout(500, 800)
        }

        return dialog
    }

    private fun snackBarError(error: String) {
        val snackBar = view?.let {
            Snackbar.make(it, error, Snackbar.LENGTH_LONG)
        }
        val color =
            view?.let { MaterialColors.getColor(it, androidx.appcompat.R.attr.colorError) }
        if (color != null) {
            snackBar?.setBackgroundTint(color)
        }
        snackBar?.show()
    }

    private fun checkToken() {
        when (viewModel.getToken()) {
            null -> {
                //
            }
            else -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_homeFragment)
//                navController.popBackStack(R.id.loginFragment, true )
            }
        }
    }

    companion object {
        @JvmStatic
        val devman: String = Build.MANUFACTURER

        @JvmStatic
        val devmod: String = Build.MODEL

        @JvmStatic
        val devavs: String = Build.VERSION.RELEASE

        @JvmStatic
        val devaid: String = Build.VERSION.SDK_INT.toString()
    }
}