package com.example.mindvocab.screens.settings.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.repeat.RepeatSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsRepeatViewModel @Inject constructor(
    private val repeatSettings: RepeatSettings
) : BaseViewModel() {

    private val _answeringVariantSetting = MutableLiveData<RepeatSettings.AnsweringVariantSetting>()
    val answeringVariantSetting: LiveData<RepeatSettings.AnsweringVariantSetting> get() = _answeringVariantSetting

    private val _questionVariantSetting = MutableLiveData<RepeatSettings.QuestionVariantSetting>()
    val questionVariantSetting: LiveData<RepeatSettings.QuestionVariantSetting> get() = _questionVariantSetting

    private val _cardAnimationSetting = MutableLiveData<RepeatSettings.CardAnimationSetting>()
    val cardAnimationSetting: LiveData<RepeatSettings.CardAnimationSetting> get() = _cardAnimationSetting

    init {
        getAnsweringVariantSetting()
        getQuestionVariantSetting()
        getCardAnimationSetting()
    }

    fun setAnsweringVariantSetting(setting: RepeatSettings.AnsweringVariantSetting) {
        viewModelScope.launch {
            repeatSettings.setAnsweringVariantSetting(setting)
        }
    }

    fun setQuestionVariantSetting(setting: RepeatSettings.QuestionVariantSetting) {
        viewModelScope.launch {
            repeatSettings.setQuestionVariantSetting(setting)
        }
    }

    fun setCardAnimationSetting(setting: RepeatSettings.CardAnimationSetting) {
        viewModelScope.launch {
            repeatSettings.setCardAnimationSetting(setting)
        }
    }

    private fun getAnsweringVariantSetting() {
        viewModelScope.launch {
            repeatSettings.answeringVariantSetting.collect {
                _answeringVariantSetting.value = it
            }
        }
    }

    private fun getQuestionVariantSetting() {
        viewModelScope.launch {
            repeatSettings.questionVariantSetting.collect {
                _questionVariantSetting.value = it
            }
        }
    }

    private fun getCardAnimationSetting() {
        viewModelScope.launch {
            repeatSettings.cardAnimationSetting.collect {
                _cardAnimationSetting.value = it
            }
        }
    }

}