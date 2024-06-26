package com.example.mindvocab.screens.settings.repeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.databinding.FragmentSettingsRepeatBinding
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.CardAnimationSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import com.example.mindvocab.screens.settings.BaseSettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsRepeatFragment : BaseSettingsFragment() {

    override val viewModel by viewModels<SettingsRepeatViewModel>()

    private var _binding: FragmentSettingsRepeatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsRepeatBinding.inflate(inflater, container, false)

        initialBinding()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialBinding() {
        viewModel.answeringVariantSettingLiveData.observe(viewLifecycleOwner) {
            binding.answeringVariantSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.answeringVariantSettingContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.repeating_variant_settings),
                    subtitle = requireContext().getString(R.string.repeating_variant_settings_helping_text),
                    setting = it,
                    settingClass = AnsweringVariantSetting::class.java
                ) { selectedSetting ->
                    viewModel.setAnsweringVariantSetting(selectedSetting)
                }
            }
        }

        viewModel.questionVariantSettingLiveData.observe(viewLifecycleOwner) {
            binding.questionVariantSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.questionVariantSettingContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.first_show_settings),
                    subtitle = requireContext().getString(R.string.first_show_settings_helping_text),
                    setting = it,
                    settingClass = QuestionVariantSetting::class.java
                ) { selectedSetting ->
                    viewModel.setQuestionVariantSetting(selectedSetting)
                }
            }
        }

        viewModel.cardAnimationSettingLiveData.observe(viewLifecycleOwner) {
            binding.cardAnimationSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.cardAnimationSettingContainer.setOnClickListener { _ ->
                showMultipleChoiceSettingsDialog(
                    title = requireContext().getString(R.string.turning_animation_settings),
                    subtitle = requireContext().getString(R.string.turning_animation_settings_helping_text),
                    setting = it,
                    settingClass = CardAnimationSetting::class.java
                ) { selectedSetting ->
                    viewModel.setCardAnimationSetting(selectedSetting)
                }
            }
        }
    }

}