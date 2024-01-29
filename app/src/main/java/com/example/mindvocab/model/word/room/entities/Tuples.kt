package com.example.mindvocab.model.word.room.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithExamplesAndTranslationsTuple(
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
)