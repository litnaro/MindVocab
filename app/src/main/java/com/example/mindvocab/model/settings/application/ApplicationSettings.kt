package com.example.mindvocab.model.settings.application

import com.example.mindvocab.model.settings.AppSettingsEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ApplicationSettings {

    // Application theme
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

    val applicationTheme: MutableStateFlow<ApplicationTheme>

    suspend fun setApplicationTheme(theme: ApplicationTheme)

    // Application  interface language
    enum class ApplicationLanguage(val value: Int) : AppSettingsEnum {
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

    // User native language for learning purposes
    enum class NativeLanguage(val value: Int) : AppSettingsEnum {
        ENGLISH(0),
        RUSSIAN(1),
        UKRAINIAN(2);

        companion object {
            fun fromValue(value: Int): NativeLanguage {
                return NativeLanguage.entries.firstOrNull { it.value == value } ?: RUSSIAN
            }
        }
    }

    fun listenApplicationNativeLanguage(): Flow<NativeLanguage>

    suspend fun getApplicationNativeLanguage() : NativeLanguage

    suspend fun setApplicationNativeLanguage(language: NativeLanguage)
}