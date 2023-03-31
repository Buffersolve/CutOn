package com.buffersolve.cuton.feature.logout.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buffersolve.cuton.R
import com.buffersolve.cuton.databinding.FragmentLogoutBinding

class LogoutFragment : Fragment() {

    private val binding: FragmentLogoutBinding by lazy { FragmentLogoutBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}