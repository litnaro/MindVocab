package com.example.mindvocab.model.settings.repeat.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class QuestionVariantSetting(val value: Int) : AppSettingsEnum {
    WORD(0),
    TRANSLATION(1);

    companion object {
        fun fromValue(value: Int): QuestionVariantSetting {
            return entries.firstOrNull { it.value == value } ?: WORD
        }
    }
}