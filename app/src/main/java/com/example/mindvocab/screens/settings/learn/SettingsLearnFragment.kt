package com.example.mindvocab.screens.settings.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindvocab.databinding.FragmentSettingsLearnBinding

class SettingsLearnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsLearnBinding.inflate(inflater, container, false)
        return binding.root
    }
}