package com.example.mindvocab.screens.settings.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.databinding.FragmentSettingsApplicationBinding

class SettingsApplicationFragment : Fragment() {

    private val viewModel: SettingsApplicationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

}