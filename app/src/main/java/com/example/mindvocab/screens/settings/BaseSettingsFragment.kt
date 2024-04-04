package com.example.mindvocab.screens.settings

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.example.mindvocab.R
import com.example.mindvocab.core.BaseFragment
import com.example.mindvocab.databinding.DialogSettingsBottomSheetBinding
import com.google.android.material.chip.Chip
import com.google.android.material.color.MaterialColors

abstract class BaseSettingsFragment : BaseFragment() {

    @Suppress("UNCHECKED_CAST")
    fun <T : Enum<T>> showMultipleChoiceSettingsDialog(
        title: String,
        subtitle: String,
        setting: T,
        settingClass: Class<T>,
        onSelect: (T) -> Unit
    ) {

        val dialogBinding = DialogSettingsBottomSheetBinding.inflate(layoutInflater)
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
                chipBackgroundColor = MaterialColors.getColorStateListOrNull(
                    context,
                    com.google.android.material.R.attr.colorPrimaryContainer
                )
                setTextColor(MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimaryContainer, Color.WHITE))
                isCheckable = true
                tag = it
                if (it.name == setting.name) {
                    isChecked = true
                    chipBackgroundColor = MaterialColors.getColorStateListOrNull(
                        context,
                        com.google.android.material.R.attr.colorTertiaryContainer
                    )
                    chipStrokeColor = MaterialColors.getColorStateListOrNull(
                        context,
                        com.google.android.material.R.attr.colorOnPrimaryContainer
                    )
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