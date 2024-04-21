package com.example.mindvocab.model.settings.learn

import com.example.mindvocab.model.settings.learn.options.SwipeActionsSetting
import com.example.mindvocab.model.settings.learn.options.WordsADaySetting
import com.example.mindvocab.model.settings.learn.options.WordsOrderSetting
import kotlinx.coroutines.flow.MutableStateFlow

interface LearningSettings {

    val listenAfterAppearanceSetting: MutableStateFlow<Boolean>

    val wordsADaySetting: MutableStateFlow<WordsADaySetting>

    val leftSwipeAction: MutableStateFlow<SwipeActionsSetting>

    val rightSwipeAction: MutableStateFlow<SwipeActionsSetting>

    val wordsOrderSetting: MutableStateFlow<WordsOrderSetting>

    suspend fun setListenAfterAppearanceSetting(setting: Boolean)

    suspend fun setWordsADay(setting: WordsADaySetting)

    suspend fun setLeftSwipeAction(setting: SwipeActionsSetting)

    suspend fun setRightSwipeAction(setting: SwipeActionsSetting)

    suspend fun setWordsOrderSetting(setting: WordsOrderSetting)

}