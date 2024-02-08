package com.example.mindvocab.model.settings.application

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

    fun getApplicationTheme() : ApplicationTheme

    fun setApplicationTheme(theme: ApplicationTheme)

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

    fun getApplicationLanguage() : ApplicationLanguage

    fun setApplicationLanguage(language: ApplicationLanguage)

}