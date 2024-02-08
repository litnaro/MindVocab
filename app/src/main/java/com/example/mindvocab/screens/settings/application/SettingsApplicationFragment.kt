package com.example.mindvocab.screens.settings.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsApplicationBinding

class SettingsApplicationFragment : BaseFragment() {

    override val viewModel: SettingsApplicationViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsApplicationBinding.inflate(inflater, container, false)

        viewModel.themeSetting.observe(viewLifecycleOwner) {
            it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
        }

        viewModel.languageSetting.observe(viewLifecycleOwner) {
            it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
        }

        return binding.root
    }

}