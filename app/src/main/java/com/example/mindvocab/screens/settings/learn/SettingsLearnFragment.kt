package com.example.mindvocab.screens.settings.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsLearnBinding
import com.example.mindvocab.model.settings.learn.LearnSettings
import com.example.mindvocab.screens.settings.BaseSettingsFragment

class SettingsLearnFragment : BaseSettingsFragment() {

    override val viewModel: SettingsLearnViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsLearnBinding.inflate(inflater, container, false)

        viewModel.listenAfterAppearanceSetting.observe(viewLifecycleOwner) {
            binding.listenAfterAppearanceSettingSwitch.isChecked = it
        }

        binding.listenAfterAppearanceSettingSwitch.setOnCheckedChangeListener {
            _, state ->
            viewModel.setListenAfterAppearanceSetting(state)
        }

        viewModel.wordsADaySetting.observe(viewLifecycleOwner) {
            binding.wordsADaySettingsValue.text = it.value.toString()
            binding.wordsADaySettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.words_a_day_settings),
                    subtitle = requireContext().getString(R.string.words_a_day_settings_helping_text),
                    setting = it,
                    settingClass = LearnSettings.WordsADaySetting::class.java,
                ) { selectedSetting ->
                    viewModel.setWordsADaySetting(selectedSetting)
                }
            }
        }

        viewModel.leftSwipeAction.observe(viewLifecycleOwner) {
            binding.leftActionSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.leftActionSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.left_swipe_settings),
                    subtitle = requireContext().getString(R.string.left_swipe_settings_helping_text),
                    setting = it,
                    settingClass = LearnSettings.SwipeActionsSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setLeftSwipeAction(selectedSetting)
                }
            }
        }

        viewModel.rightSwipeAction.observe(viewLifecycleOwner) {
            binding.rightActionSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.rightActionSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.right_swipe_setting),
                    subtitle = requireContext().getString(R.string.right_swipe_setting_helping_text),
                    setting = it,
                    settingClass = LearnSettings.SwipeActionsSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setRightSwipeAction(selectedSetting)
                }
            }
        }

        viewModel.wordsOrderSetting.observe(viewLifecycleOwner) {
            binding.wordsOrderSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.wordsOrderSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.word_order_settings),
                    subtitle = requireContext().getString(R.string.word_order_settings_helping_text),
                    setting = it,
                    settingClass = LearnSettings.WordsOrderSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setWordsOrderSetting(selectedSetting)
                }
            }
        }

        return binding.root
    }
}