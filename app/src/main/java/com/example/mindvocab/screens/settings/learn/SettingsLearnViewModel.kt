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

    private val _listenAfterAppearanceSetting = MutableLiveData<Boolean>()
    val listenAfterAppearanceSetting: LiveData<Boolean> = _listenAfterAppearanceSetting

    private val _wordsADaySetting = MutableLiveData<WordsADaySetting>()
    val wordsADaySetting: LiveData<WordsADaySetting> = _wordsADaySetting

    private val _leftSwipeAction = MutableLiveData<SwipeActionsSetting>()
    val leftSwipeAction: LiveData<SwipeActionsSetting> = _leftSwipeAction

    private val _rightSwipeAction = MutableLiveData<SwipeActionsSetting>()
    val rightSwipeAction: LiveData<SwipeActionsSetting> = _rightSwipeAction

    private val _wordsOrderSetting = MutableLiveData<WordsOrderSetting>()
    val wordsOrderSetting: LiveData<WordsOrderSetting> = _wordsOrderSetting

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

    fun setWordsADaySetting(setting: WordsADaySetting) {
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

    fun setLeftSwipeAction(settings: SwipeActionsSetting) {
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

    fun setRightSwipeAction(settings: SwipeActionsSetting) {
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

    fun setWordsOrderSetting(settings: WordsOrderSetting) {
        viewModelScope.launch {
            learningSettings.setWordsOrderSetting(settings)
        }
    }

}