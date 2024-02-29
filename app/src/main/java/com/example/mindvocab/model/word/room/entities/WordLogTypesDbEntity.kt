package com.example.mindvocab.model.word.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_logs_types"
)
data class WordLogTypesDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val description: String
)
