package com.buffersolve.cuton.feature.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buffersolve.cuton.databinding.FragmentHomeBinding
import com.buffersolve.cuton.feature.home.data.remote.api.models.Items
import com.buffersolve.cuton.feature.home.ui.adapter.HomeAdapter
import com.buffersolve.cuton.feature.home.ui.state.ItemState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
//    private val adapter by lazy { HomeAdapter() }

    private val viewModel: HomeViewModel by viewModels()
//    private lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRV()

        // Get User Info
        viewModel.getUserInfo()

        // Get Home Menu Items
        viewModel.getHomeMenuItems()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.itemState.collect { state ->
                    when (state) {
                        is ItemState.Loading -> {
                            // Show Loading
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is ItemState.Success -> {
                            // Hide Loading
//                            adapter.submitList(state.list)
                            with(binding) {
                                recyclerView.adapter = HomeAdapter(listOf(state.answer.items))
                                progressBar.visibility = View.GONE
                            }

                        }
                        is ItemState.Error -> {
                            // Hide Loading
                            // Show Error
                        }
                    }
                }
            }
        }


    }

//    private fun initRV() {
//        binding.recyclerView.adapter = adapter
//    }

}