package com.example.mindvocab.model.settings.application

import android.content.Context

class SharedPreferencesApplicationSettings(
    appContext: Context
) : ApplicationSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // Application theme
    override fun getApplicationTheme(): ApplicationSettings.ApplicationTheme {
        return ApplicationSettings.ApplicationTheme.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_THEME, ApplicationSettings.ApplicationTheme.DEFAULT.value)
        )
    }

    override fun setApplicationTheme(theme: ApplicationSettings.ApplicationTheme) {
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_THEME, theme.value)
            .apply()
    }

    // Application language
    override fun getApplicationLanguage(): ApplicationSettings.ApplicationLanguage {
        return ApplicationSettings.ApplicationLanguage.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_LANGUAGE, ApplicationSettings.ApplicationLanguage.DEFAULT.value)
        )
    }

    override fun setApplicationLanguage(language: ApplicationSettings.ApplicationLanguage) {
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_LANGUAGE, language.value)
            .apply()
    }

    companion object {
        private const val PREF_CURRENT_APPLICATION_THEME = "currentApplicationTheme"
        private const val PREF_CURRENT_APPLICATION_LANGUAGE = "currentApplicationLanguage"
    }
}