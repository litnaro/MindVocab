package com.example.mindvocab.screens.settings.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsNotificationsBinding

class SettingsNotificationsFragment : BaseFragment() {

    override val viewModel: SettingsNotificationsViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsNotificationsBinding.inflate(layoutInflater, container, false)

        viewModel.isNotificationsEnabled.observe(viewLifecycleOwner) {
            binding.notificationsSwitch.isChecked = it
        }

        viewModel.isReminderEnabled.observe(viewLifecycleOwner) {
            binding.reminderSwitch.isChecked = it
        }


        return binding.root
    }

}