package com.example.mindvocab.screens.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mindvocab.databinding.FragmentSettingsBinding
import com.example.mindvocab.screens.settings.account.SettingsAccountFragment
import com.example.mindvocab.screens.settings.additional.SettingsAdditionalFragment
import com.example.mindvocab.screens.settings.application.SettingsApplicationFragment
import com.example.mindvocab.screens.settings.learn.SettingsLearnFragment
import com.example.mindvocab.screens.settings.notifications.SettingsNotificationsFragment
import com.example.mindvocab.screens.settings.repeat.SettingsRepeatFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.accountSettingsContainer.id, SettingsAccountFragment())
            .commit()

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.applicationSettingsContainer.id, SettingsApplicationFragment())
            .commit()

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.learnSettingsContainer.id, SettingsLearnFragment())
            .commit()

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.repeatSettingsContainer.id, SettingsRepeatFragment())
            .commit()

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.notificationSettingsContainer.id, SettingsNotificationsFragment())
            .commit()

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.additionalSettingsContainer.id, SettingsAdditionalFragment())
            .commit()

        return binding.root
    }
}