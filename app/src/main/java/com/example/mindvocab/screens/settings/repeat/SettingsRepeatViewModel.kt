package com.example.mindvocab.screens.settings.repeat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.repeat.RepeatSettings
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.CardAnimationSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsRepeatViewModel @Inject constructor(
    private val repeatSettings: RepeatSettings
) : BaseViewModel() {

    private val _answeringVariantSettingLiveData = MutableLiveData<AnsweringVariantSetting>()
    val answeringVariantSettingLiveData: LiveData<AnsweringVariantSetting> get() = _answeringVariantSettingLiveData

    private val _questionVariantSettingLiveData = MutableLiveData<QuestionVariantSetting>()
    val questionVariantSettingLiveData: LiveData<QuestionVariantSetting> get() = _questionVariantSettingLiveData

    private val _cardAnimationSettingLiveData = MutableLiveData<CardAnimationSetting>()
    val cardAnimationSettingLiveData: LiveData<CardAnimationSetting> get() = _cardAnimationSettingLiveData

    init {
        getAnsweringVariantSetting()
        getQuestionVariantSetting()
        getCardAnimationSetting()
    }

    fun setAnsweringVariantSetting(setting: AnsweringVariantSetting) {
        viewModelScope.launch {
            repeatSettings.setAnsweringVariantSetting(setting)
        }
    }

    fun setQuestionVariantSetting(setting: QuestionVariantSetting) {
        viewModelScope.launch {
            repeatSettings.setQuestionVariantSetting(setting)
        }
    }

    fun setCardAnimationSetting(setting: CardAnimationSetting) {
        viewModelScope.launch {
            repeatSettings.setCardAnimationSetting(setting)
        }
    }

    private fun getAnsweringVariantSetting() {
        viewModelScope.launch {
            repeatSettings.answeringVariantSetting.collect {
                _answeringVariantSettingLiveData.value = it
            }
        }
    }

    private fun getQuestionVariantSetting() {
        viewModelScope.launch {
            repeatSettings.questionVariantSetting.collect {
                _questionVariantSettingLiveData.value = it
            }
        }
    }

    private fun getCardAnimationSetting() {
        viewModelScope.launch {
            repeatSettings.cardAnimationSetting.collect {
                _cardAnimationSettingLiveData.value = it
            }
        }
    }

}