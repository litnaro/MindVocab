package com.example.mindvocab.model.settings.learn.options

import com.example.mindvocab.model.settings.AppSettingsEnum

enum class SwipeActionsSetting(val value: Int) : AppSettingsEnum {
    KNOW(0),
    LEARN(1);

    companion object {
        fun fromValue(value: Int): SwipeActionsSetting {
            return entries.firstOrNull { it.value == value } ?: KNOW
        }
    }
}