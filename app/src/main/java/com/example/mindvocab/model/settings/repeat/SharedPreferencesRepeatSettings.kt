package com.example.mindvocab.model.settings.repeat

import android.content.Context
import com.example.mindvocab.model.settings.AppSettings
import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.CardAnimationSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SharedPreferencesRepeatSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : RepeatSettings, AppSettings(appContext) {

    override val answeringVariantSetting = MutableStateFlow(getAnsweringVariantSetting())

    override val questionVariantSetting = MutableStateFlow(getQuestionVariantSetting())

    override val cardAnimationSetting = MutableStateFlow(getCardAnimationSetting())

    // Answering variants settings

    override suspend fun setAnsweringVariantSetting(setting: AnsweringVariantSetting) {
        if (getAnsweringVariantSetting() == setting) return

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, setting.value)
            .apply()
        answeringVariantSetting.emit(getAnsweringVariantSetting())

        when(setting) {
            AnsweringVariantSetting.TRANSLATION -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, QuestionVariantSetting.WORD.value)
                    .apply()
            }
            AnsweringVariantSetting.GRAMMAR -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, QuestionVariantSetting.TRANSLATION.value)
                    .apply()
            }
        }
        questionVariantSetting.emit(getQuestionVariantSetting())
    }

    private fun getAnsweringVariantSetting() : AnsweringVariantSetting {
        return AnsweringVariantSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, AnsweringVariantSetting.TRANSLATION.value)
        )
    }

    // Question variant settings

    override suspend fun setQuestionVariantSetting(setting: QuestionVariantSetting) {
        if (getQuestionVariantSetting() == setting) return

        sharedPreferences.edit()
            .putInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, setting.value)
            .apply()
        questionVariantSetting.emit(getQuestionVariantSetting())

        when(setting) {
            QuestionVariantSetting.WORD -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, AnsweringVariantSetting.TRANSLATION.value)
                    .apply()
            }
            QuestionVariantSetting.TRANSLATION -> {
                sharedPreferences.edit()
                    .putInt(PREF_CURRENT_ANSWERING_VARIANT_SETTING, AnsweringVariantSetting.GRAMMAR.value)
                    .apply()
            }
        }
        answeringVariantSetting.emit(getAnsweringVariantSetting())
    }

    private fun getQuestionVariantSetting() : QuestionVariantSetting {
        return QuestionVariantSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_QUESTION_VARIANT_SETTING, QuestionVariantSetting.WORD.value)
        )
    }

    // Card animation settings

    override suspend fun setCardAnimationSetting(setting: CardAnimationSetting) {
        if (getCardAnimationSetting() == setting) return
        sharedPreferences.edit()
            .putInt(PREF_CURRENT_CARD_ANIMATION_SETTING, setting.value)
            .apply()
        cardAnimationSetting.emit(getCardAnimationSetting())
    }

    private fun getCardAnimationSetting() : CardAnimationSetting {
        return CardAnimationSetting.fromValue(
            sharedPreferences.getInt(PREF_CURRENT_CARD_ANIMATION_SETTING, CardAnimationSetting.FLIP.value)
        )
    }

    companion object {
        private const val PREF_CURRENT_ANSWERING_VARIANT_SETTING = "currentAnsweringVariantSetting"
        private const val PREF_CURRENT_QUESTION_VARIANT_SETTING = "currentQuestionVariantSetting"
        private const val PREF_CURRENT_CARD_ANIMATION_SETTING = "currentCardAnimationSetting"
    }
}