package com.example.mindvocab.model.settings.learn.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class WordsOrderSetting(val value: Int) : AppSettingsEnum {
    SMART(0),
    RANDOM(1);

    companion object {
        fun fromValue(value: Int): WordsOrderSetting {
            return entries.firstOrNull { it.value == value } ?: SMART
        }
    }
}