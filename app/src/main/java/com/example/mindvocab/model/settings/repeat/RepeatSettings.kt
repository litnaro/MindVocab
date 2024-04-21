package com.example.mindvocab.model.settings.repeat

import com.example.mindvocab.model.settings.repeat.options.AnsweringVariantSetting
import com.example.mindvocab.model.settings.repeat.options.CardAnimationSetting
import com.example.mindvocab.model.settings.repeat.options.QuestionVariantSetting
import kotlinx.coroutines.flow.MutableStateFlow

interface RepeatSettings {

    val answeringVariantSetting: MutableStateFlow<AnsweringVariantSetting>

    val questionVariantSetting: MutableStateFlow<QuestionVariantSetting>

    val cardAnimationSetting: MutableStateFlow<CardAnimationSetting>

    suspend fun setAnsweringVariantSetting(setting: AnsweringVariantSetting)

    suspend fun setQuestionVariantSetting(setting: QuestionVariantSetting)

    suspend fun setCardAnimationSetting(setting: CardAnimationSetting)

}