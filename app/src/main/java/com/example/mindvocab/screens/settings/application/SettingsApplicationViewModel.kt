package com.example.mindvocab.screens.settings.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.application.ApplicationSettings
import kotlinx.coroutines.launch

class SettingsApplicationViewModel(
    private val applicationSettings: ApplicationSettings
) : BaseViewModel() {

    private val _themeSetting = MutableLiveData<ApplicationSettings.ApplicationTheme>()
    val themeSetting: LiveData<ApplicationSettings.ApplicationTheme> get() = _themeSetting

    private val _languageSetting = MutableLiveData<ApplicationSettings.ApplicationLanguage>()
    val languageSetting: LiveData<ApplicationSettings.ApplicationLanguage> get() = _languageSetting

    private val _nativeLanguageSetting = MutableLiveData<ApplicationSettings.NativeLanguage>()
    val nativeLanguageSetting: LiveData<ApplicationSettings.NativeLanguage> get() = _nativeLanguageSetting

    init {
        getTheme()
        getLanguage()
        getNativeLanguage()
    }

    private fun getTheme() {
        viewModelScope.launch {
            applicationSettings.applicationTheme.collect {
                _themeSetting.value = it
            }
        }
    }

    fun setTheme(theme: ApplicationSettings.ApplicationTheme) {
        viewModelScope.launch {
            applicationSettings.setApplicationTheme(theme)
        }
    }

    private fun getLanguage() {
        viewModelScope.launch {
            applicationSettings.applicationLanguage.collect {
                _languageSetting.value = it
            }
        }
    }

    fun setLanguage(language: ApplicationSettings.ApplicationLanguage) {
        viewModelScope.launch {
            applicationSettings.setApplicationLanguage(language)
        }
    }

    private fun getNativeLanguage() {
        viewModelScope.launch {
            applicationSettings.listenApplicationNativeLanguage().collect {
                _nativeLanguageSetting.value = it
            }
        }
        viewModelScope.launch {
            applicationSettings.getApplicationNativeLanguage()
        }
    }

    fun setNativeLanguage(language: ApplicationSettings.NativeLanguage) {
        viewModelScope.launch {
            applicationSettings.setApplicationNativeLanguage(language)
        }
    }

}