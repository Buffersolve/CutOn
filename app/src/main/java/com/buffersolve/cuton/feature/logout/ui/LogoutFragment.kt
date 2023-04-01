package com.buffersolve.cuton.feature.logout.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.buffersolve.cuton.R
import com.buffersolve.cuton.databinding.FragmentLogoutBinding
import com.buffersolve.cuton.feature.logout.ui.state.LogoutState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogoutFragment : Fragment() {

    private val binding: FragmentLogoutBinding by lazy {
        FragmentLogoutBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: LogoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            yesButton.setOnClickListener {

                // Logout
                viewModel.logout()

                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.CREATED) {
                        viewModel.logoutState.collect { state ->
                            when (state) {
                                is LogoutState.Success -> {
                                    // Hide Loading
                                    viewModel.deleteToken()
                                    Navigation.findNavController(it)
                                        .navigate(R.id.action_logoutFragment_to_loginFragment)
                                    // Show Success Message

                                }
                                else -> {
                                    // Show Error Message
                                }
                            }
                        }
                    }
                }
            }
            noButton.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }

        }
    }

}