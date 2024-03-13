package com.example.mindvocab.screens.settings.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mindvocab.core.BaseViewModel
import com.example.mindvocab.model.settings.learn.LearningSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsLearnViewModel @Inject constructor(
    private val learningSettings: LearningSettings
) : BaseViewModel() {

    private val _listenAfterAppearanceSetting = MutableLiveData<Boolean>()
    val listenAfterAppearanceSetting: LiveData<Boolean> = _listenAfterAppearanceSetting

    private val _wordsADaySetting = MutableLiveData<LearningSettings.WordsADaySetting>()
    val wordsADaySetting: LiveData<LearningSettings.WordsADaySetting> = _wordsADaySetting

    private val _leftSwipeAction = MutableLiveData<LearningSettings.SwipeActionsSetting>()
    val leftSwipeAction: LiveData<LearningSettings.SwipeActionsSetting> = _leftSwipeAction

    private val _rightSwipeAction = MutableLiveData<LearningSettings.SwipeActionsSetting>()
    val rightSwipeAction: LiveData<LearningSettings.SwipeActionsSetting> = _rightSwipeAction

    private val _wordsOrderSetting = MutableLiveData<LearningSettings.WordsOrderSetting>()
    val wordsOrderSetting: LiveData<LearningSettings.WordsOrderSetting> = _wordsOrderSetting

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
                _listenAfterAppearanceSetting.value = it
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
                _wordsADaySetting.value = it
            }
        }
    }

    fun setWordsADaySetting(setting: LearningSettings.WordsADaySetting) {
        viewModelScope.launch {
            learningSettings.setWordsADay(setting)
        }
    }

    private fun getLeftSwipeAction() {
        viewModelScope.launch {
            learningSettings.leftSwipeAction.collect {
                _leftSwipeAction.value = it
            }
        }
    }

    fun setLeftSwipeAction(settings: LearningSettings.SwipeActionsSetting) {
        viewModelScope.launch {
            learningSettings.setLeftSwipeAction(settings)
        }
    }

    private fun getRightSwipeAction() {
        viewModelScope.launch {
            learningSettings.rightSwipeAction.collect {
                _rightSwipeAction.value = it
            }
        }
    }

    fun setRightSwipeAction(settings: LearningSettings.SwipeActionsSetting) {
        viewModelScope.launch {
            learningSettings.setRightSwipeAction(settings)
        }
    }

    private fun getWordsOrderSetting() {
        viewModelScope.launch {
            learningSettings.wordsOrderSetting.collect {
                _wordsOrderSetting.value = it
            }
        }
    }

    fun setWordsOrderSetting(settings: LearningSettings.WordsOrderSetting) {
        viewModelScope.launch {
            learningSettings.setWordsOrderSetting(settings)
        }
    }

}