package com.example.mindvocab.model.settings.learn

import com.example.mindvocab.model.settings.AppSettingsEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface LearningSettings {

    // Listen after appearance

    val listenAfterAppearanceSetting: Flow<Boolean>

    suspend fun setListenAfterAppearanceSetting(setting: Boolean)

    // Words a day

    enum class WordsADaySetting(val value: Int) : AppSettingsEnum {
        EASY(3),
        MEDIUM(5),
        HARD(7),
        EXTREME(10),
        LEGENDARY(15);

        companion object {
            fun fromValue(value: Int): WordsADaySetting {
                return entries.firstOrNull { it.value == value } ?: MEDIUM
            }
        }
    }

    val wordsADaySetting: MutableStateFlow<WordsADaySetting>

    suspend fun setWordsADay(setting: WordsADaySetting)

    //Swipe action settings

    enum class SwipeActionsSetting(val value: Int) : AppSettingsEnum{
        KNOW(0),
        LEARN(1);

        companion object {
            fun fromValue(value: Int): SwipeActionsSetting {
                return entries.firstOrNull { it.value == value } ?: KNOW
            }
        }
    }

    val leftSwipeAction: MutableStateFlow<SwipeActionsSetting>

    suspend fun setLeftSwipeAction(setting: SwipeActionsSetting)

    val rightSwipeAction: MutableStateFlow<SwipeActionsSetting>

    suspend fun setRightSwipeAction(setting: SwipeActionsSetting)

    // Words order

    enum class WordsOrderSetting(val value: Int) : AppSettingsEnum{
        SMART(0),
        RANDOM(1);

        companion object {
            fun fromValue(value: Int): WordsOrderSetting {
                return entries.firstOrNull { it.value == value } ?: SMART
            }
        }
    }

    val wordsOrderSetting: MutableStateFlow<WordsOrderSetting>

    suspend fun setWordsOrderSetting(setting: WordsOrderSetting)

}