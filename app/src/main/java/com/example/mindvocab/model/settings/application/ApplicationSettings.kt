package com.example.mindvocab.model.settings.application

import com.example.mindvocab.model.settings.application.options.ApplicationLanguage
import com.example.mindvocab.model.settings.application.options.ApplicationTheme
import com.example.mindvocab.model.settings.application.options.NativeLanguage
import kotlinx.coroutines.flow.MutableStateFlow

interface ApplicationSettings {

    val applicationTheme: MutableStateFlow<ApplicationTheme>

    val applicationLanguage: MutableStateFlow<ApplicationLanguage>

    val nativeLanguage: MutableStateFlow<NativeLanguage>

    suspend fun setApplicationTheme(theme: ApplicationTheme)

    suspend fun setApplicationLanguage(language: ApplicationLanguage)

    suspend fun setApplicationNativeLanguage(language: NativeLanguage)
}