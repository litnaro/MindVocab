package com.example.mindvocab.model.settings.learn

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import com.example.mindvocab.model.settings.learn.options.SwipeActionsSetting
import com.example.mindvocab.model.settings.learn.options.WordsADaySetting
import com.example.mindvocab.model.settings.learn.options.WordsOrderSetting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedPreferencesLearningSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : LearningSettings, AppSettings(appContext) {

    override val listenAfterAppearanceSetting = MutableStateFlow(getListenAfterAppearanceSetting())

    override val wordsADaySetting = MutableStateFlow(getWordsADaySetting())

    override val leftSwipeAction = MutableStateFlow(getLeftSwipeAction())

    override val rightSwipeAction = MutableStateFlow(getRightSwipeAction())

    override val wordsOrderSetting = MutableStateFlow(getWordsOrderSetting())

    // Listen after appearance

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

    override suspend fun setWordsADay(setting: WordsADaySetting) {
        if (getWordsADaySetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_A_DAY_SETTING, setting.value)
            .apply()
        wordsADaySetting.emit(setting)
    }

    private fun getWordsADaySetting() : WordsADaySetting {
        return WordsADaySetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_A_DAY_SETTING, WordsADaySetting.MEDIUM.value)
        )
    }

    //Swipe action settings

    override suspend fun setLeftSwipeAction(setting: SwipeActionsSetting) {
        when(setting) {
            SwipeActionsSetting.LEARN -> {
                setSwipeActions(setting, SwipeActionsSetting.KNOW)
            }
            SwipeActionsSetting.KNOW -> {
                setSwipeActions(setting, SwipeActionsSetting.LEARN)
            }
        }
    }

    private fun getLeftSwipeAction() : SwipeActionsSetting {
        return SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_LEFT_SWIPE_ACTION, SwipeActionsSetting.LEARN.value)
        )
    }

    override suspend fun setRightSwipeAction(setting: SwipeActionsSetting) {
        when(setting) {
            SwipeActionsSetting.LEARN -> {
                setSwipeActions(SwipeActionsSetting.KNOW ,setting)
            }
            SwipeActionsSetting.KNOW -> {
                setSwipeActions(SwipeActionsSetting.LEARN ,setting)
            }
        }
    }

    private fun getRightSwipeAction() : SwipeActionsSetting {
        return SwipeActionsSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_RIGHT_SWIPE_ACTION, SwipeActionsSetting.KNOW.value)
        )
    }

    private suspend fun setSwipeActions(leftAction: SwipeActionsSetting, rightAction: SwipeActionsSetting) {
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

    override suspend fun setWordsOrderSetting(setting: WordsOrderSetting) {
        if (getWordsOrderSetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_WORDS_ORDER_SETTING, setting.value)
            .apply()
        wordsOrderSetting.emit(setting)
    }

    private fun getWordsOrderSetting() : WordsOrderSetting {
        return WordsOrderSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_WORDS_ORDER_SETTING, WordsOrderSetting.SMART.value)
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