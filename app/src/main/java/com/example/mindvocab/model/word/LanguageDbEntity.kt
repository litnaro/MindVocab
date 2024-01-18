package com.example.mindvocab.model.word

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "languages"
)
data class LanguageDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)
