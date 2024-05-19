package com.example.mindvocab.screens.settings.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentSettingsLearnBinding
import com.example.mindvocab.model.settings.learn.options.SwipeActionsSetting
import com.example.mindvocab.model.settings.learn.options.WordsADaySetting
import com.example.mindvocab.model.settings.learn.options.WordsOrderSetting
import com.example.mindvocab.screens.settings.BaseSettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsLearnFragment : BaseSettingsFragment() {

    override val viewModel by viewModels<SettingsLearnViewModel>()

    private var _binding: FragmentSettingsLearnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsLearnBinding.inflate(inflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
        viewModel.listenAfterAppearanceSettingLiveData.observe(viewLifecycleOwner) {
            binding.listenAfterAppearanceSettingSwitch.isChecked = it
        }

        binding.listenAfterAppearanceSettingSwitch.setOnCheckedChangeListener {
                _, state ->
            viewModel.setListenAfterAppearanceSetting(state)
        }

        viewModel.wordsADaySettingLiveData.observe(viewLifecycleOwner) {
            binding.wordsADaySettingsValue.text = it.value.toString()
            binding.wordsADaySettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.words_a_day_settings),
                    subtitle = requireContext().getString(R.string.words_a_day_settings_helping_text),
                    setting = it,
                    settingClass = WordsADaySetting::class.java,
                ) { selectedSetting ->
                    viewModel.setWordsADaySetting(selectedSetting)
                }
            }
        }

        viewModel.leftSwipeActionLiveData.observe(viewLifecycleOwner) {
            binding.leftActionSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.leftActionSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.left_swipe_settings),
                    subtitle = requireContext().getString(R.string.left_swipe_settings_helping_text),
                    setting = it,
                    settingClass = SwipeActionsSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setLeftSwipeAction(selectedSetting)
                }
            }
        }

        viewModel.rightSwipeActionLiveData.observe(viewLifecycleOwner) {
            binding.rightActionSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.rightActionSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.right_swipe_setting),
                    subtitle = requireContext().getString(R.string.right_swipe_setting_helping_text),
                    setting = it,
                    settingClass = SwipeActionsSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setRightSwipeAction(selectedSetting)
                }
            }
        }

        viewModel.wordsOrderSettingLiveData.observe(viewLifecycleOwner) {
            binding.wordsOrderSettingsValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.wordsOrderSettingsContainer.setOnClickListener {  _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.word_order_settings),
                    subtitle = requireContext().getString(R.string.word_order_settings_helping_text),
                    setting = it,
                    settingClass = WordsOrderSetting::class.java,
                ) { selectedSetting ->
                    viewModel.setWordsOrderSetting(selectedSetting)
                }
            }
        }
    }
}