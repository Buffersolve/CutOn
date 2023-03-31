package com.buffersolve.cuton.feature.home.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.buffersolve.cuton.R
import com.buffersolve.cuton.databinding.FragmentHomeBinding
import com.buffersolve.cuton.feature.home.ui.adapter.HomeAdapter
import com.buffersolve.cuton.feature.home.ui.state.ItemState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val adapter by lazy { HomeAdapter() }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar
        setupMenu()

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

                                // RV adapter
                                recyclerView.adapter = adapter
                                adapter.list = listOf(state.answer.items)


                                // Hide Loading
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

        // RV click listener
        adapter.setOnItemClickListener {

            // Navigate to CatalogFragment
            val navController = Navigation.findNavController(view)
            navController.navigate(
                R.id.action_homeFragment_to_catalogFragment
            )

        }

    }

    // Toolbar
    private fun setupMenu() {

        // Left button
        val toolbar = binding.topAppBar
        toolbar.setNavigationOnClickListener {
            Log.d("onMenuItemSelected", "onMenuItemSelected: ")

        }

        // Right button
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tool_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.app_bar_user -> {
                        // User Info
                        viewModel.getUserInfo()
                        true
                    }
                    else -> false
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}