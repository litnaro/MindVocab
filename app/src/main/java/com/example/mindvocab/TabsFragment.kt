package com.example.mindvocab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mindvocab.databinding.FragmentTabsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTabsBinding.inflate(inflater, container, false)

        val navHost = childFragmentManager.findFragmentById(binding.tabsContainer.id) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        return binding.root
    }

}