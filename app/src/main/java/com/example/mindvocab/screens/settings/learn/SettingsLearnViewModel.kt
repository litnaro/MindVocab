package com.example.mindvocab.screens.settings.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.learn.LearnSettings
import kotlinx.coroutines.launch

class SettingsLearnViewModel(
    private val learnSettings: LearnSettings
) : BaseViewModel() {

    private val _listenAfterAppearanceSetting = MutableLiveData<Boolean>()
    val listenAfterAppearanceSetting: LiveData<Boolean> = _listenAfterAppearanceSetting

    private val _wordsADaySetting = MutableLiveData<LearnSettings.WordsADaySetting>()
    val wordsADaySetting: LiveData<LearnSettings.WordsADaySetting> = _wordsADaySetting

    private val _leftSwipeAction = MutableLiveData<LearnSettings.SwipeActionsSetting>()
    val leftSwipeAction: LiveData<LearnSettings.SwipeActionsSetting> = _leftSwipeAction

    private val _rightSwipeAction = MutableLiveData<LearnSettings.SwipeActionsSetting>()
    val rightSwipeAction: LiveData<LearnSettings.SwipeActionsSetting> = _rightSwipeAction

    private val _wordsOrderSetting = MutableLiveData<LearnSettings.WordsOrderSetting>()
    val wordsOrderSetting: LiveData<LearnSettings.WordsOrderSetting> = _wordsOrderSetting

    init {
        getListenAfterAppearanceSetting()
        getWordsADaySetting()
        getLeftSwipeAction()
        getRightSwipeAction()
        getWordsOrderSetting()
    }

    private fun getListenAfterAppearanceSetting() {
        viewModelScope.launch {
            learnSettings.listenAfterAppearanceSetting.collect {
                _listenAfterAppearanceSetting.value = it
            }
        }
    }

    fun setListenAfterAppearanceSetting(setting: Boolean) {
        viewModelScope.launch {
            learnSettings.setListenAfterAppearanceSetting(setting)
        }
    }

    private fun getWordsADaySetting() {
        viewModelScope.launch {
            learnSettings.wordsADaySetting.collect {
                _wordsADaySetting.value = it
            }
        }
    }

    fun setWordsADaySetting(setting: LearnSettings.WordsADaySetting) {
        viewModelScope.launch {
            learnSettings.setWordsADay(setting)
        }
    }

    private fun getLeftSwipeAction() {
        viewModelScope.launch {
            learnSettings.leftSwipeAction.collect {
                _leftSwipeAction.value = it
            }
        }
    }

    fun setLeftSwipeAction(settings: LearnSettings.SwipeActionsSetting) {
        viewModelScope.launch {
            learnSettings.setLeftSwipeAction(settings)
        }
    }

    private fun getRightSwipeAction() {
        viewModelScope.launch {
            learnSettings.rightSwipeAction.collect {
                _rightSwipeAction.value = it
            }
        }
    }

    fun setRightSwipeAction(settings: LearnSettings.SwipeActionsSetting) {
        viewModelScope.launch {
            learnSettings.setRightSwipeAction(settings)
        }
    }

    private fun getWordsOrderSetting() {
        viewModelScope.launch {
            learnSettings.wordsOrderSetting.collect {
                _wordsOrderSetting.value = it
            }
        }
    }

    fun setWordsOrderSetting(settings: LearnSettings.WordsOrderSetting) {
        viewModelScope.launch {
            learnSettings.setWordsOrderSetting(settings)
        }
    }

}