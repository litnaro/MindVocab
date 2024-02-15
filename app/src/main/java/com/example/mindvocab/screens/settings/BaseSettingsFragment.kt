package com.example.mindvocab.screens.settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.SettingsBottomSheetDialogBinding
import com.google.android.material.chip.Chip

abstract class BaseSettingsFragment : BaseFragment() {

    @Suppress("UNCHECKED_CAST")
    fun <T : Enum<T>> showMultipleChoiceSettingsDialog(
        title: String,
        subtitle: String,
        setting: T,
        settingClass: Class<T>,
        onSelect: (T) -> Unit
    ) {

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

        dialogBinding.dialogTitle.text = title
        dialogBinding.dialogHelpingText.text = subtitle

        settingClass.enumConstants?.forEach {
            val chip = Chip(requireContext()).apply {
                text = it.name.lowercase().replaceFirstChar { char -> char.uppercase() }
                setTextColor(Color.WHITE)
                isCheckable = true
                tag = it
                if (it.name == setting.name) {
                    isChecked = true
                }
            }
            dialogBinding.dialogChipGroup.addView(chip)
        }

        dialogBinding.dialogChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val currentSelection = group.findViewById<Chip>(checkedIds[0]).tag as T
            onSelect(currentSelection)
            dialog.dismiss()
        }

        dialogBinding.dialogCloseButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}