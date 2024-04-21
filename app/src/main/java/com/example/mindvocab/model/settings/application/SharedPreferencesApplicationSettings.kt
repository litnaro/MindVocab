package com.example.mindvocab.model.settings.application

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import com.example.mindvocab.model.settings.application.options.ApplicationLanguage
import com.example.mindvocab.model.settings.application.options.ApplicationTheme
import com.example.mindvocab.model.settings.application.options.NativeLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedPreferencesApplicationSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : ApplicationSettings, AppSettings(appContext) {

    override val applicationTheme = MutableStateFlow(getApplicationTheme())

    override val applicationLanguage = MutableStateFlow(getApplicationLanguage())

    override val nativeLanguage = MutableStateFlow(getApplicationNativeLanguage())

    // Application theme

    override suspend fun setApplicationTheme(theme: ApplicationTheme) {
        if (getApplicationTheme() == theme) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_THEME, theme.value)
            .apply()
        applicationTheme.emit(getApplicationTheme())
    }

    private fun getApplicationTheme(): ApplicationTheme {
        return ApplicationTheme.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_THEME, ApplicationTheme.DEFAULT.value)
        )
    }

    // Application interface language

    override suspend fun setApplicationLanguage(language: ApplicationLanguage) {
        if (getApplicationLanguage() == language) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_LANGUAGE, language.value)
            .apply()
        applicationLanguage.emit(language)
    }

    private fun getApplicationLanguage(): ApplicationLanguage {
        return ApplicationLanguage.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_LANGUAGE, ApplicationLanguage.DEFAULT.value)
        )
    }

    // User native language

    override suspend fun setApplicationNativeLanguage(language: NativeLanguage) {
        if (getApplicationNativeLanguage() == language) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE, language.value)
            .apply()
        nativeLanguage.emit(language)
    }

    private fun getApplicationNativeLanguage(): NativeLanguage {
        return NativeLanguage.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE, NativeLanguage.UKRAINIAN.value)
        )
    }

    companion object {
        private const val PREF_CURRENT_APPLICATION_THEME = "currentApplicationTheme"
        private const val PREF_CURRENT_APPLICATION_LANGUAGE = "currentApplicationLanguage"
        private const val PREF_CURRENT_APPLICATION_NATIVE_LANGUAGE = "currentApplicationNativeLanguage"
    }
}