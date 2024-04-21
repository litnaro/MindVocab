package com.example.mindvocab.model.settings.learn.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class WordsADaySetting(val value: Int) : AppSettingsEnum {
    EASY(3),
    MEDIUM(5),
    HARD(7),
    EXTREME(10),
    LEGENDARY(15);

    companion object {
        fun fromValue(value: Int): WordsADaySetting {
            return entries.firstOrNull { it.value == value } ?: MEDIUM
        }
    }
}