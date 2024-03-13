package com.example.mindvocab.screens.settings.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentSettingsApplicationBinding
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.screens.settings.BaseSettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsApplicationFragment : BaseSettingsFragment() {

    override val viewModel by viewModels<SettingsApplicationViewModel>()

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
                    title = requireContext().getString(R.string.interface_language_settings),
                    subtitle = requireContext().getString(R.string.application_language_settings_helping_text),
                    setting = it,
                    settingClass = ApplicationSettings.ApplicationLanguage::class.java
                ) { onSelectSettings ->
                    viewModel.setLanguage(onSelectSettings)
                }
            }
        }

        viewModel.nativeLanguageSetting.observe(viewLifecycleOwner) {
            binding.nativeLanguageSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.nativeLanguageSettingsContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.interface_native_language_settings),
                    subtitle = requireContext().getString(R.string.application_native_language_settings_helping_text),
                    setting = it,
                    settingClass = ApplicationSettings.NativeLanguage::class.java
                ) { onSelectSettings ->
                    viewModel.setNativeLanguage(onSelectSettings)
                }
            }
        }

        return binding.root
    }

}