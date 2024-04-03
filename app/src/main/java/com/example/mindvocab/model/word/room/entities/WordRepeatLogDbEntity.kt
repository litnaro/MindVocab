package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_repeat_logs",
    foreignKeys = [
        ForeignKey(
            entity = AccountWordProgressDbEntity::class,
            parentColumns = ["account_id", "word_id"],
            childColumns = ["account_id", "word_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
)
data class WordRepeatLogDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "repeat_date") val repeatDate: Long
)