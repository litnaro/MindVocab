package com.example.mindvocab.model.settings.application

import kotlinx.coroutines.flow.MutableStateFlow

interface ApplicationSettings {

    // Application theme
    enum class ApplicationTheme(val value: Int) {
        DEFAULT(0),
        LIGHT(1),
        DARK(2);

        companion object {
            fun fromValue(value: Int): ApplicationTheme {
                return entries.firstOrNull { it.value == value } ?: DEFAULT
            }
        }
    }

    val applicationTheme: MutableStateFlow<ApplicationTheme>

    suspend fun setApplicationTheme(theme: ApplicationTheme)

    // Application language
    enum class ApplicationLanguage(val value: Int) {
        DEFAULT(0),
        ENGLISH(1),
        UKRAINIAN(2),
        RUSSIAN(3);

        companion object {
            fun fromValue(value: Int): ApplicationLanguage {
                return ApplicationLanguage.entries.firstOrNull { it.value == value } ?: DEFAULT
            }
        }
    }

    val applicationLanguage: MutableStateFlow<ApplicationLanguage>

    suspend fun setApplicationLanguage(language: ApplicationLanguage)

}