package com.example.mindvocab.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentTabsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : BaseFragment() {

    override val viewModel by viewModels<TabsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTabsBinding.inflate(inflater, container, false)

        val navHost = childFragmentManager.findFragmentById(binding.tabsContainer.id) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        viewModel.recentAchievementsCount.observe(viewLifecycleOwner) {
            if (it == 0) {
                binding.bottomNavigationView.getOrCreateBadge(R.id.statistic).apply {
                    isVisible = false
                }
            } else {
                binding.bottomNavigationView.getOrCreateBadge(R.id.statistic).apply {
                    isVisible = true
                    number = it
                }
            }
        }

        return binding.root
    }

}