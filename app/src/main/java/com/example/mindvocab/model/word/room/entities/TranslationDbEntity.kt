package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "translations",
    foreignKeys = [
        ForeignKey(
            entity = LanguageDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["language_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["word_id", "language_id"],
    indices = [
        Index("language_id"),
        Index("word_id")
    ]
)
data class TranslationDbEntity(
    @ColumnInfo(name = "language_id") val languageId: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    val translation: String
)
