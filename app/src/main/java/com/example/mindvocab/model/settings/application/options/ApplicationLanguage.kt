package com.example.mindvocab.model.settings.application.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class ApplicationLanguage(val value: Int) : AppSettingsEnum {
    DEFAULT(0),
    ENGLISH(1),
    UKRAINIAN(2),
    RUSSIAN(3);

    companion object {
        fun fromValue(value: Int): ApplicationLanguage {
            return entries.firstOrNull { it.value == value } ?: DEFAULT
        }
    }
}