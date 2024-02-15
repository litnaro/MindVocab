package com.example.mindvocab.screens.settings.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsApplicationBinding
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.screens.settings.BaseSettingsFragment

class SettingsApplicationFragment : BaseSettingsFragment() {

    override val viewModel: SettingsApplicationViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsApplicationBinding.inflate(inflater, container, false)

        viewModel.themeSetting.observe(viewLifecycleOwner) {
            binding.themeSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.themeSettingsContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.application_theme_settings),
                    subtitle = requireContext().getString(R.string.application_theme_settings_helping_text),
                    setting = it,
                    settingClass = ApplicationSettings.ApplicationTheme::class.java,
                ) { selectedSetting ->
                    viewModel.setTheme(selectedSetting)
                }
            }
        }

        viewModel.languageSetting.observe(viewLifecycleOwner) {
            binding.languageSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.languageSettingsContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    settingClass = ApplicationSettings.ApplicationLanguage::class.java,
                    setting = it,
                    title = requireContext().getString(R.string.interface_language_settings),
                    subtitle = requireContext().getString(R.string.application_language_settings_helping_text)
                ) { onSelectSettings ->
                    viewModel.setLanguage(onSelectSettings)
                }
            }
        }

        return binding.root
    }

}