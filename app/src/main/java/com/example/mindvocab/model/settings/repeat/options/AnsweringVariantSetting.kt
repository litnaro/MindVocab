package com.example.mindvocab.model.settings.repeat.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class AnsweringVariantSetting(val value: Int) : AppSettingsEnum {
    TRANSLATION(0),
    GRAMMAR(1);

    companion object {
        fun fromValue(value: Int): AnsweringVariantSetting {
            return AnsweringVariantSetting.entries.firstOrNull { it.value == value } ?: TRANSLATION
        }
    }
}