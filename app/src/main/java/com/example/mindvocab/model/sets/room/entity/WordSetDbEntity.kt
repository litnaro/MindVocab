package com.example.mindvocab.model.sets.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_sets"
)
data class WordSetDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val image: String
)
