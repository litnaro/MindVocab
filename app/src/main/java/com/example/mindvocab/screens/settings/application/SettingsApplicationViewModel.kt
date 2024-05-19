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

    private val _themeSettingLiveData = MutableLiveData<ApplicationTheme>()
    val themeSettingLiveData: LiveData<ApplicationTheme> get() = _themeSettingLiveData

    private val _languageSettingLiveData = MutableLiveData<ApplicationLanguage>()
    val languageSettingLiveData: LiveData<ApplicationLanguage> get() = _languageSettingLiveData

    private val _nativeLanguageSettingLiveData = MutableLiveData<NativeLanguage>()
    val nativeLanguageSettingLiveData: LiveData<NativeLanguage> get() = _nativeLanguageSettingLiveData

    init {
        getTheme()
        getLanguage()
        getNativeLanguage()
    }

    private fun getTheme() {
        viewModelScope.launch {
            applicationSettings.applicationTheme.collect {
                _themeSettingLiveData.value = it
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
                _languageSettingLiveData.value = it
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
                _nativeLanguageSettingLiveData.value = it
            }
        }
    }

    fun setNativeLanguage(language: NativeLanguage) {
        viewModelScope.launch {
            applicationSettings.setApplicationNativeLanguage(language)
        }
    }

}