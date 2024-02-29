package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity

@Entity(
    tableName = "words",
    foreignKeys = [
        ForeignKey(
            entity = WordSetDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_set_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("word_set_id")
    ]
)
data class WordDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val word: String,
    val image: String,
    val audio: String,
    val transcription: String,
    val explanation: String,
    @ColumnInfo(name = "word_set_id") val wordSetId: Long
)