package com.example.mindvocab.model.settings.learn

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesLearnSettings(
    appContext: Context
) : LearnSettings {

    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    // Listen after appearance

    override val listenAfterAppearanceSetting = MutableStateFlow(getListenAfterAppearanceSetting())

    override suspend fun setListenAfterAppearanceSetting(setting: Boolean) {
        if (getListenAfterAppearanceSetting() == setting) return
        sharedPreferences.edit()
            .putBoolean(PREF_CURRENT_LISTEN_AFTER_APPEARANCE_SETTING, setting)
            .apply()
        listenAfterAppearanceSetting.emit(setting)
    }

    private fun getListenAfterAppearanceSetting(): Boolean {
        return sharedPreferences.getBoolean(PREF_CURRENT_LISTEN_AFTER_APPEARANCE_SETTING, true)
    }

    // Words a day

    override val wordsADaySetting = MutableStateFlow(getWordsADaySetting())

    override suspend fun setWordsADay(setting: LearnSettings.WordsADaySetting) {
        if (getWordsADaySetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_A_DAY_SETTING, setting.value)
            .apply()
        wordsADaySetting.emit(setting)
    }

    private fun getWordsADaySetting() : LearnSettings.WordsADaySetting {
        return LearnSettings.WordsADaySetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_A_DAY_SETTING, LearnSettings.WordsADaySetting.MEDIUM.value)
        )
    }

    //Swipe action settings

    override val leftSwipeAction = MutableStateFlow(getLeftSwipeAction())

    override suspend fun setLeftSwipeAction(setting: LearnSettings.SwipeActionsSetting) {
        when(setting) {
            LearnSettings.SwipeActionsSetting.LEARN -> {
                setSwipeActions(setting, LearnSettings.SwipeActionsSetting.KNOW)
            }
            LearnSettings.SwipeActionsSetting.KNOW -> {
                setSwipeActions(setting, LearnSettings.SwipeActionsSetting.LEARN)
            }
        }
    }

    private fun getLeftSwipeAction() : LearnSettings.SwipeActionsSetting {
        return LearnSettings.SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_LEFT_SWIPE_ACTION, LearnSettings.SwipeActionsSetting.LEARN.value)
        )
    }

    override val rightSwipeAction = MutableStateFlow(getRightSwipeAction())

    override suspend fun setRightSwipeAction(setting: LearnSettings.SwipeActionsSetting) {
        when(setting) {
            LearnSettings.SwipeActionsSetting.LEARN -> {
                setSwipeActions(LearnSettings.SwipeActionsSetting.KNOW ,setting)
            }
            LearnSettings.SwipeActionsSetting.KNOW -> {
                setSwipeActions(LearnSettings.SwipeActionsSetting.LEARN ,setting)
            }
        }
    }

    private fun getRightSwipeAction() : LearnSettings.SwipeActionsSetting {
        return LearnSettings.SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_RIGHT_SWIPE_ACTION, LearnSettings.SwipeActionsSetting.KNOW.value)
        )
    }

    private suspend fun setSwipeActions(leftAction: LearnSettings.SwipeActionsSetting, rightAction: LearnSettings.SwipeActionsSetting) {
        if (getLeftSwipeAction() == leftAction || getRightSwipeAction() == rightAction) return

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_LEFT_SWIPE_ACTION, leftAction.value)
            .apply()
        leftSwipeAction.emit(leftAction)

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_RIGHT_SWIPE_ACTION, rightAction.value)
            .apply()
        rightSwipeAction.emit(rightAction)
    }

    // Words order

    override val wordsOrderSetting = MutableStateFlow(getWordsOrderSetting())

    override suspend fun setWordsOrderSetting(setting: LearnSettings.WordsOrderSetting) {
        if (getWordsOrderSetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_ORDER_SETTING, setting.value)
            .apply()
        wordsOrderSetting.emit(setting)
    }

    private fun getWordsOrderSetting() : LearnSettings.WordsOrderSetting {
        return LearnSettings.WordsOrderSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_ORDER_SETTING, LearnSettings.WordsOrderSetting.SMART.value)
        )
    }


    companion object {
        private const val PREF_CURRENT_LISTEN_AFTER_APPEARANCE_SETTING = "currentListenAfterAppearanceSetting"
        private const val PREF_CURRENT_WORDS_A_DAY_SETTING = "currentWordsADaySetting"
        private const val PREF_CURRENT_LEFT_SWIPE_ACTION = "currentLeftSwipeAction"
        private const val PREF_CURRENT_RIGHT_SWIPE_ACTION = "currentRightSwipeAction"
        private const val PREF_CURRENT_WORDS_ORDER_SETTING = "currentWordsOrderSetting"
    }

}