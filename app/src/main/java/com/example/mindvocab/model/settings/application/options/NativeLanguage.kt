package com.example.mindvocab.model.settings.application.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class NativeLanguage(val value: Int) : AppSettingsEnum {
    ENGLISH(0),
    UKRAINIAN(1),
    RUSSIAN(2);

    companion object {
        fun fromValue(value: Int): NativeLanguage {
            return entries.firstOrNull { it.value == value } ?: UKRAINIAN
        }
    }
}