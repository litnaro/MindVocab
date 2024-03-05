package com.example.mindvocab.model.settings.learn

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesLearningSettings(
    appContext: Context
) : LearningSettings, AppSettings(appContext) {

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

    override suspend fun setWordsADay(setting: LearningSettings.WordsADaySetting) {
        if (getWordsADaySetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_A_DAY_SETTING, setting.value)
            .apply()
        wordsADaySetting.emit(setting)
    }

    private fun getWordsADaySetting() : LearningSettings.WordsADaySetting {
        return LearningSettings.WordsADaySetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_A_DAY_SETTING, LearningSettings.WordsADaySetting.MEDIUM.value)
        )
    }

    //Swipe action settings

    override val leftSwipeAction = MutableStateFlow(getLeftSwipeAction())

    override suspend fun setLeftSwipeAction(setting: LearningSettings.SwipeActionsSetting) {
        when(setting) {
            LearningSettings.SwipeActionsSetting.LEARN -> {
                setSwipeActions(setting, LearningSettings.SwipeActionsSetting.KNOW)
            }
            LearningSettings.SwipeActionsSetting.KNOW -> {
                setSwipeActions(setting, LearningSettings.SwipeActionsSetting.LEARN)
            }
        }
    }

    private fun getLeftSwipeAction() : LearningSettings.SwipeActionsSetting {
        return LearningSettings.SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_LEFT_SWIPE_ACTION, LearningSettings.SwipeActionsSetting.LEARN.value)
        )
    }

    override val rightSwipeAction = MutableStateFlow(getRightSwipeAction())

    override suspend fun setRightSwipeAction(setting: LearningSettings.SwipeActionsSetting) {
        when(setting) {
            LearningSettings.SwipeActionsSetting.LEARN -> {
                setSwipeActions(LearningSettings.SwipeActionsSetting.KNOW ,setting)
            }
            LearningSettings.SwipeActionsSetting.KNOW -> {
                setSwipeActions(LearningSettings.SwipeActionsSetting.LEARN ,setting)
            }
        }
    }

    private fun getRightSwipeAction() : LearningSettings.SwipeActionsSetting {
        return LearningSettings.SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_RIGHT_SWIPE_ACTION, LearningSettings.SwipeActionsSetting.KNOW.value)
        )
    }

    private suspend fun setSwipeActions(leftAction: LearningSettings.SwipeActionsSetting, rightAction: LearningSettings.SwipeActionsSetting) {
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

    override suspend fun setWordsOrderSetting(setting: LearningSettings.WordsOrderSetting) {
        if (getWordsOrderSetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_ORDER_SETTING, setting.value)
            .apply()
        wordsOrderSetting.emit(setting)
    }

    private fun getWordsOrderSetting() : LearningSettings.WordsOrderSetting {
        return LearningSettings.WordsOrderSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_ORDER_SETTING, LearningSettings.WordsOrderSetting.SMART.value)
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