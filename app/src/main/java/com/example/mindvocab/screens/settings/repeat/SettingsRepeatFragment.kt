package com.example.mindvocab.screens.settings.repeat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindvocab.databinding.FragmentSettingsRepeatBinding

class SettingsRepeatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsRepeatBinding.inflate(inflater, container, false)
        return binding.root
    }

}