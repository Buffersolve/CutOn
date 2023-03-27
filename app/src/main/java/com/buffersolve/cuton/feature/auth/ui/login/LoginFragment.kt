package com.buffersolve.cuton.feature.auth.ui.login

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.buffersolve.cuton.R
import com.buffersolve.cuton.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogin.setOnClickListener {

        }

        viewModel.appVersionValidate()
//        viewModel.test()
    }

    companion object {
        @JvmStatic val devman: String = Build.MANUFACTURER
        @JvmStatic val devmod: String = Build.MODEL
        @JvmStatic val devavs: String = Build.VERSION.RELEASE
        @JvmStatic val devid: Int = Build.VERSION.SDK_INT
    }
}