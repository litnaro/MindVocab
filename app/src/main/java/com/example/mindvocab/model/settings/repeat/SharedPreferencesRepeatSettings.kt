package com.example.mindvocab.model.settings.repeat

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesRepeatSettings(
    appContext: Context
) : RepeatSettings, AppSettings(appContext) {

    // Answering variants settings

    override val answeringVariantSetting = MutableStateFlow(getAnsweringVariantSetting())

    override suspend fun setAnsweringVariantSetting(setting: RepeatSettings.AnsweringVariantSetting) {
        if (getAnsweringVariantSetting() == setting) return

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, setting.value)
            .apply()
        answeringVariantSetting.emit(getAnsweringVariantSetting())

        when(setting) {
            RepeatSettings.AnsweringVariantSetting.TRANSLATION -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, RepeatSettings.QuestionVariantSetting.WORD.value)
                    .apply()
            }
            RepeatSettings.AnsweringVariantSetting.GRAMMAR -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, RepeatSettings.QuestionVariantSetting.TRANSLATION.value)
                    .apply()
            }
        }
        questionVariantSetting.emit(getQuestionVariantSetting())
    }

    private fun getAnsweringVariantSetting() : RepeatSettings.AnsweringVariantSetting {
        return RepeatSettings.AnsweringVariantSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, RepeatSettings.AnsweringVariantSetting.TRANSLATION.value)
        )
    }

    // Question variant settings

    override val questionVariantSetting = MutableStateFlow(getQuestionVariantSetting())

    override suspend fun setQuestionVariantSetting(setting: RepeatSettings.QuestionVariantSetting) {
        if (getQuestionVariantSetting() == setting) return

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, setting.value)
            .apply()
        questionVariantSetting.emit(getQuestionVariantSetting())

        when(setting) {
            RepeatSettings.QuestionVariantSetting.WORD -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, RepeatSettings.AnsweringVariantSetting.TRANSLATION.value)
                    .apply()
            }
            RepeatSettings.QuestionVariantSetting.TRANSLATION -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, RepeatSettings.AnsweringVariantSetting.GRAMMAR.value)
                    .apply()
            }
        }
        answeringVariantSetting.emit(getAnsweringVariantSetting())
    }

    private fun getQuestionVariantSetting() : RepeatSettings.QuestionVariantSetting {
        return RepeatSettings.QuestionVariantSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, RepeatSettings.QuestionVariantSetting.WORD.value)
        )
    }

    // Card animation settings

    override val cardAnimationSetting = MutableStateFlow(getCardAnimationSetting())

    override suspend fun setCardAnimationSetting(setting: RepeatSettings.CardAnimationSetting) {
        if (getCardAnimationSetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_CARD_ANIMATION_SETTING, setting.value)
            .apply()
        cardAnimationSetting.emit(getCardAnimationSetting())
    }

    private fun getCardAnimationSetting() : RepeatSettings.CardAnimationSetting {
        return RepeatSettings.CardAnimationSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_CARD_ANIMATION_SETTING, RepeatSettings.CardAnimationSetting.FLIP.value)
        )
    }

    companion object {
        private const val PREF_CURRENT_ANSWERING_VARIANT_SETTING = "currentAnsweringVariantSetting"
        private const val PREF_CURRENT_QUESTION_VARIANT_SETTING = "currentQuestionVariantSetting"
        private const val PREF_CURRENT_CARD_ANIMATION_SETTING = "currentCardAnimationSetting"
    }
}