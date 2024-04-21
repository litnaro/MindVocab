package com.example.mindvocab.model.settings.repeat.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class CardAnimationSetting(val value: Int) : AppSettingsEnum {
    FLIP(0),
    EXPAND(1);

    companion object {
        fun fromValue(value: Int): CardAnimationSetting {
            return entries.firstOrNull { it.value == value } ?: FLIP
        }
    }
}