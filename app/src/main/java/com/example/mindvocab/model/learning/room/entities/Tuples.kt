package com.example.mindvocab.model.learning.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.example.mindvocab.model.settings.application.ApplicationSettings
import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.room.entities.ExampleDbEntity
import com.example.mindvocab.model.word.room.entities.TranslationDbEntity
import com.example.mindvocab.model.word.room.entities.WordDbEntity

data class WordForLearningTuple(
    @Embedded val word: WordDbEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "word_id"
    )
    val example: List<ExampleDbEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "word_id"
    )
    val translation: List<TranslationDbEntity>,
) {
    fun toWord(languageSetting: ApplicationSettings.NativeLanguage) = Word(
        id = word.id,
        word = word.word,
        audio = word.audio,
        image = word.image,
        exampleList = example.map { it.sentence },
        explanation = word.explanation,
        transcription = word.transcription,
        translation = if (languageSetting != ApplicationSettings.NativeLanguage.ENGLISH) {
            try {
                translation.first { it.languageId.toInt() == languageSetting.value }.translation
            } catch (e: NoSuchElementException) {
                ""
            }
        } else {
            ""
        }
    )
}

data class UpdateWordProgressAsLearningTuple(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    @ColumnInfo(name = "started_at") val startedAt: Long
)