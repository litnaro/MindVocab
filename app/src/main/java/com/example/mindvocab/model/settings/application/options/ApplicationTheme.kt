package com.example.mindvocab.model.settings.application.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class ApplicationTheme(val value: Int) : AppSettingsEnum {
    DEFAULT(0),
    LIGHT(1),
    DARK(2);

    companion object {
        fun fromValue(value: Int): ApplicationTheme {
            return entries.firstOrNull { it.value == value } ?: DEFAULT
        }
    }
}