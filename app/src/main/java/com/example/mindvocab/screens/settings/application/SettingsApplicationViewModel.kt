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

    init {
        viewModelScope.launch {
            _themeSetting.value = applicationSettings.getApplicationTheme()
            _languageSetting.value = applicationSettings.getApplicationLanguage()
        }
    }

    fun setTheme(theme: ApplicationSettings.ApplicationTheme) {

    }

    fun setLanguage(language: ApplicationSettings.ApplicationLanguage) {

    }

}