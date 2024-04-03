package com.example.mindvocab.model.settings.repeat

import com.example.mindvocab.model.settings.AppSettingsEnum
import kotlinx.coroutines.flow.MutableStateFlow

interface RepeatSettings {

    // Answering variants settings
    enum class AnsweringVariantSetting(val value: Int) : AppSettingsEnum {
        TRANSLATION(0),
        GRAMMAR(1);

        companion object {
            fun fromValue(value: Int): AnsweringVariantSetting {
                return entries.firstOrNull { it.value == value } ?: TRANSLATION
            }
        }
    }

    val answeringVariantSetting: MutableStateFlow<AnsweringVariantSetting>

    suspend fun setAnsweringVariantSetting(setting: AnsweringVariantSetting)

    // Question variant settings
    enum class QuestionVariantSetting(val value: Int) : AppSettingsEnum{
        WORD(0),
        TRANSLATION(1);

        companion object {
            fun fromValue(value: Int): QuestionVariantSetting {
                return entries.firstOrNull { it.value == value } ?: WORD
            }
        }
    }

    val questionVariantSetting: MutableStateFlow<QuestionVariantSetting>

    suspend fun setQuestionVariantSetting(setting: QuestionVariantSetting)

    // Card animation settings
    enum class CardAnimationSetting(val value: Int) : AppSettingsEnum{
        FLIP(0),
        EXPAND(1);

        companion object {
            fun fromValue(value: Int): CardAnimationSetting {
                return entries.firstOrNull { it.value == value } ?: FLIP
            }
        }
    }

    val cardAnimationSetting: MutableStateFlow<CardAnimationSetting>

    suspend fun setCardAnimationSetting(setting: CardAnimationSetting)

}