package com.example.mindvocab.screens.settings.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.learn.LearningSettings
import com.example.mindvocab.model.settings.learn.options.SwipeActionsSetting
import com.example.mindvocab.model.settings.learn.options.WordsADaySetting
import com.example.mindvocab.model.settings.learn.options.WordsOrderSetting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsLearnViewModel @Inject constructor(
    private val learningSettings: LearningSettings
) : BaseViewModel() {

    private val _listenAfterAppearanceSettingLiveData = MutableLiveData<Boolean>()
    val listenAfterAppearanceSettingLiveData: LiveData<Boolean> = _listenAfterAppearanceSettingLiveData

    private val _wordsADaySettingLiveData = MutableLiveData<WordsADaySetting>()
    val wordsADaySettingLiveData: LiveData<WordsADaySetting> = _wordsADaySettingLiveData

    private val _leftSwipeActionLiveData = MutableLiveData<SwipeActionsSetting>()
    val leftSwipeActionLiveData: LiveData<SwipeActionsSetting> = _leftSwipeActionLiveData

    private val _rightSwipeActionLiveData = MutableLiveData<SwipeActionsSetting>()
    val rightSwipeActionLiveData: LiveData<SwipeActionsSetting> = _rightSwipeActionLiveData

    private val _wordsOrderSettingLiveData = MutableLiveData<WordsOrderSetting>()
    val wordsOrderSettingLiveData: LiveData<WordsOrderSetting> = _wordsOrderSettingLiveData

    init {
        getListenAfterAppearanceSetting()
        getWordsADaySetting()
        getLeftSwipeAction()
        getRightSwipeAction()
        getWordsOrderSetting()
    }

    private fun getListenAfterAppearanceSetting() {
        viewModelScope.launch {
            learningSettings.listenAfterAppearanceSetting.collect {
                _listenAfterAppearanceSettingLiveData.value = it
            }
        }
    }

    fun setListenAfterAppearanceSetting(setting: Boolean) {
        viewModelScope.launch {
            learningSettings.setListenAfterAppearanceSetting(setting)
        }
    }

    private fun getWordsADaySetting() {
        viewModelScope.launch {
            learningSettings.wordsADaySetting.collect {
                _wordsADaySettingLiveData.value = it
            }
        }
    }

    fun setWordsADaySetting(setting: WordsADaySetting) {
        viewModelScope.launch {
            learningSettings.setWordsADay(setting)
        }
    }

    private fun getLeftSwipeAction() {
        viewModelScope.launch {
            learningSettings.leftSwipeAction.collect {
                _leftSwipeActionLiveData.value = it
            }
        }
    }

    fun setLeftSwipeAction(settings: SwipeActionsSetting) {
        viewModelScope.launch {
            learningSettings.setLeftSwipeAction(settings)
        }
    }

    private fun getRightSwipeAction() {
        viewModelScope.launch {
            learningSettings.rightSwipeAction.collect {
                _rightSwipeActionLiveData.value = it
            }
        }
    }

    fun setRightSwipeAction(settings: SwipeActionsSetting) {
        viewModelScope.launch {
            learningSettings.setRightSwipeAction(settings)
        }
    }

    private fun getWordsOrderSetting() {
        viewModelScope.launch {
            learningSettings.wordsOrderSetting.collect {
                _wordsOrderSettingLiveData.value = it
            }
        }
    }

    fun setWordsOrderSetting(settings: WordsOrderSetting) {
        viewModelScope.launch {
            learningSettings.setWordsOrderSetting(settings)
        }
    }

}