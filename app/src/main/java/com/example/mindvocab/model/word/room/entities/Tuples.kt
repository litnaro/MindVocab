package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.example.mindvocab.model.word.WordCalculations
import com.example.mindvocab.model.word.entities.Word
import com.example.mindvocab.model.word.entities.WordStatistic

data class WordWithStatisticTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    val translation: String,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Int,
    @ColumnInfo(name = "started_at") val startedAt: Long,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long
) {
    fun toWordStatistic() = WordStatistic(
        id = id,
        word = word,
        transcription = transcription,
        translation = translation,
        isSelected = isSelected,
        learnProgress = WordCalculations.getProgressOfWord(this),
        wordStatus = WordCalculations.getWordStatusByStatistic(this)
    )
}

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
    fun toWord() = Word(
        id = word.id,
        word = word.word,
        audio = word.audio,
        image = word.image,
        exampleList = example.map { entity -> entity.sentence },
        explanation = word.explanation,
        transcription = word.transcription,
        translation = translation.map { entity -> entity.translation }.toString()
    )

//    fun toWord() = Word(
//        id = this.id,
//        word = this.word,
//        audio = "",
//        image = this.image,
//        exampleList = emptyList(),
//        explanation = this.explanation,
//        transcription = this.transcription,
//        translation = ""
//    )
}