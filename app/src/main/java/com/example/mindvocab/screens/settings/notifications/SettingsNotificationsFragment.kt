package com.example.mindvocab.screens.settings.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.FragmentSettingsNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsNotificationsFragment : BaseFragment() {

    override val viewModel by viewModels<SettingsNotificationsViewModel>()

    private var _binding: FragmentSettingsNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsNotificationsBinding.inflate(layoutInflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
        viewModel.isNotificationsEnabledLiveData.observe(viewLifecycleOwner) {
            binding.notificationsSwitch.isChecked = it
        }

        binding.notificationsSwitch.setOnCheckedChangeListener {
                _, state ->
            viewModel.setNotifications(state)
        }

        viewModel.isReminderEnabledLiveData.observe(viewLifecycleOwner) {
            binding.reminderSwitch.isChecked = it
        }

        binding.reminderSwitch.setOnCheckedChangeListener {
                _, state ->
            viewModel.setReminder(state)
        }
    }

}