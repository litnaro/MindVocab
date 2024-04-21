package com.example.mindvocab.screens.settings.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.settings.application.options.ApplicationLanguage
import com.example.mindvocab.model.settings.application.options.ApplicationTheme
import com.example.mindvocab.model.settings.application.options.NativeLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsApplicationViewModel @Inject constructor(
    private val applicationSettings: ApplicationSettings
) : BaseViewModel() {

    private val _themeSetting = MutableLiveData<ApplicationTheme>()
    val themeSetting: LiveData<ApplicationTheme> get() = _themeSetting

    private val _languageSetting = MutableLiveData<ApplicationLanguage>()
    val languageSetting: LiveData<ApplicationLanguage> get() = _languageSetting

    private val _nativeLanguageSetting = MutableLiveData<NativeLanguage>()
    val nativeLanguageSetting: LiveData<NativeLanguage> get() = _nativeLanguageSetting

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

    fun setTheme(theme: ApplicationTheme) {
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

    fun setLanguage(language: ApplicationLanguage) {
        viewModelScope.launch {
            applicationSettings.setApplicationLanguage(language)
        }
    }

    private fun getNativeLanguage() {
        viewModelScope.launch {
            applicationSettings.nativeLanguage.collect {
                _nativeLanguageSetting.value = it
            }
        }
    }

    fun setNativeLanguage(language: NativeLanguage) {
        viewModelScope.launch {
            applicationSettings.setApplicationNativeLanguage(language)
        }
    }

}