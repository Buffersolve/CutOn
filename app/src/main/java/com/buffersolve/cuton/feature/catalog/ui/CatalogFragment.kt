package com.buffersolve.cuton.feature.catalog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.buffersolve.cuton.databinding.FragmentCatalogBinding
import com.buffersolve.cuton.feature.catalog.ui.adapter.CatalogAdapter
import com.buffersolve.cuton.feature.catalog.ui.state.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private val binding: FragmentCatalogBinding by lazy { FragmentCatalogBinding.inflate(layoutInflater) }
    private val viewModel: CatalogViewModel by viewModels()
    private val adapter by lazy { CatalogAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get Catalog
        viewModel.getCatalog()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.catalogState.collect { state ->
                    when (state) {
                        is HomeState.Loading -> {
                            // Show Loading
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is HomeState.Success -> {
                            with(binding) {

                                // RV adapter
                                recyclerView.adapter = adapter
                                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

                                // Set Data
                                adapter.list = state.answer

                                // Hide Loading
                                progressBar.visibility = View.GONE
                            }

                        }
                        is HomeState.Error -> {
                            // Hide Loading
                            binding.progressBar.visibility = View.GONE
                        }
                    }


                }
            }
        }

    }


}