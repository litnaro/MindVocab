package com.example.mindvocab.model.settings.application

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesApplicationSettings(
    appContext: Context
) : ApplicationSettings, AppSettings(appContext) {

    // Application theme

    override val applicationTheme = MutableStateFlow(getApplicationTheme())

    override suspend fun setApplicationTheme(theme: ApplicationSettings.ApplicationTheme) {
        if (getApplicationTheme() == theme) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_THEME, theme.value)
            .apply()
        applicationTheme.emit(getApplicationTheme())
    }

    private fun getApplicationTheme(): ApplicationSettings.ApplicationTheme {
        return ApplicationSettings.ApplicationTheme.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_THEME, ApplicationSettings.ApplicationTheme.DEFAULT.value)
        )
    }

    // Application interface language

    override val applicationLanguage = MutableStateFlow(getApplicationLanguage())

    override suspend fun setApplicationLanguage(language: ApplicationSettings.ApplicationLanguage) {
        if (getApplicationLanguage() == language) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_LANGUAGE, language.value)
            .apply()
        applicationLanguage.emit(language)
    }

    private fun getApplicationLanguage(): ApplicationSettings.ApplicationLanguage {
        return ApplicationSettings.ApplicationLanguage.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_LANGUAGE, ApplicationSettings.ApplicationLanguage.DEFAULT.value)
        )
    }

    // User native language

    private val applicationNativeLanguageFlow = MutableSharedFlow<ApplicationSettings.NativeLanguage>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun setApplicationNativeLanguage(language: ApplicationSettings.NativeLanguage) {
        if (getApplicationNativeLanguage() == language) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE, language.value)
            .apply()
        applicationNativeLanguageFlow.emit(language)
    }

    override suspend fun getApplicationNativeLanguage(): ApplicationSettings.NativeLanguage {
        val setting = ApplicationSettings.NativeLanguage.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE, ApplicationSettings.NativeLanguage.RUSSIAN.value)
        )
        applicationNativeLanguageFlow.emit(setting)
        return setting
    }

    override fun listenApplicationNativeLanguage() = applicationNativeLanguageFlow

    companion object {
        private const val PREF_CURRENT_APPLICATION_THEME = "currentApplicationTheme"
        private const val PREF_CURRENT_APPLICATION_LANGUAGE = "currentApplicationLanguage"
        private const val PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE = "currentApplicationNativeLanguage"
    }
}