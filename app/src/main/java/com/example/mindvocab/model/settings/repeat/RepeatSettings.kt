package com.example.mindvocab.model.settings.repeat

import kotlinx.coroutines.flow.MutableStateFlow

interface RepeatSettings {

    // Answering variants settings
    enum class AnsweringVariantSetting(val value: Int) {
        TRANSLATION(0),
        GRAMMAR(1);

        companion object {
            fun fromValue(value: Int): AnsweringVariantSetting {
                return AnsweringVariantSetting.entries.firstOrNull { it.value == value } ?: TRANSLATION
            }
        }
    }

    val answeringVariantSetting: MutableStateFlow<AnsweringVariantSetting>

    suspend fun setAnsweringVariantSetting(setting: AnsweringVariantSetting)

    // Question variant settings
    enum class QuestionVariantSetting(val value: Int) {
        WORD(0),
        TRANSLATION(1);

        companion object {
            fun fromValue(value: Int): QuestionVariantSetting {
                return QuestionVariantSetting.entries.firstOrNull { it.value == value } ?: WORD
            }
        }
    }

    val questionVariantSetting: MutableStateFlow<QuestionVariantSetting>

    suspend fun setQuestionVariantSetting(setting: QuestionVariantSetting)

    // Card animation settings
    enum class CardAnimationSetting(val value: Int) {
        FLIP(0),
        EXPAND(1);

        companion object {
            fun fromValue(value: Int): CardAnimationSetting {
                return CardAnimationSetting.entries.firstOrNull { it.value == value } ?: FLIP
            }
        }
    }

    val cardAnimationSetting: MutableStateFlow<CardAnimationSetting>

    suspend fun setCardAnimationSetting(setting: CardAnimationSetting)

}