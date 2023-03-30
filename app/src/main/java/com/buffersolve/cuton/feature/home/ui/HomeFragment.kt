package com.buffersolve.cuton.feature.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.buffersolve.cuton.databinding.FragmentHomeBinding
import com.buffersolve.cuton.feature.home.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val adapter by lazy { HomeAdapter() }

    private val viewModel: HomeViewModel by viewModels()
//    private lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get User Info
        viewModel.getUserInfo()

        initRV()


    }

    fun initRV() {
        binding.recyclerView.adapter = adapter
    }

}