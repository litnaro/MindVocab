package com.example.mindvocab.screens.settings.application

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.core.factory
import com.example.mindvocab.databinding.FragmentSettingsApplicationBinding
import com.example.mindvocab.databinding.SettingsBottomSheetDialogBinding
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.google.android.material.chip.Chip

class SettingsApplicationFragment : BaseFragment() {

    override val viewModel: SettingsApplicationViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsApplicationBinding.inflate(inflater, container, false)

        viewModel.themeSetting.observe(viewLifecycleOwner) {
            binding.themeSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
            binding.themeSettingsContainer.setOnClickListener { _ ->
                showThemeSettingsDialog(it)
            }
        }

        viewModel.languageSetting.observe(viewLifecycleOwner) {
            binding.languageSettingValue.text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
        }

        return binding.root
    }

    private fun showThemeSettingsDialog(currentSetting: ApplicationSettings.ApplicationTheme) {
        val dialogBinding = SettingsBottomSheetDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }

        dialogBinding.dialogTitle.text = requireContext().getString(R.string.application_theme_settings)
        dialogBinding.dialogHelpingText.text = requireContext().getString(R.string.application_theme_settings_helping_text)

        dialogBinding.dialogCloseButton.setOnClickListener {
            dialog.dismiss()
        }

        ApplicationSettings.ApplicationTheme.entries.forEach {
            val chip = Chip(requireContext()).apply {
                text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
                setTextColor(Color.WHITE)
                isCheckable = true
                tag = it
                if (it.name == currentSetting.name) {
                    isChecked = true
                }
            }
            dialogBinding.dialogChipGroup.addView(chip)
        }

        dialogBinding.dialogChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val currentSelection = group.findViewById<Chip>(checkedIds[0]).tag as ApplicationSettings.ApplicationTheme
            viewModel.setTheme(currentSelection)
            Toast.makeText(requireContext(), currentSelection.name, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}