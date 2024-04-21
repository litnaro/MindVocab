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

    private val _answeringVariantSetting = MutableLiveData<AnsweringVariantSetting>()
    val answeringVariantSetting: LiveData<AnsweringVariantSetting> get() = _answeringVariantSetting

    private val _questionVariantSetting = MutableLiveData<QuestionVariantSetting>()
    val questionVariantSetting: LiveData<QuestionVariantSetting> get() = _questionVariantSetting

    private val _cardAnimationSetting = MutableLiveData<CardAnimationSetting>()
    val cardAnimationSetting: LiveData<CardAnimationSetting> get() = _cardAnimationSetting

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