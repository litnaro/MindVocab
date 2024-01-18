package com.example.mindvocab.model.sets.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.mindvocab.model.account.room.entities.AccountDbEntity

@Entity(
    tableName = "account_word_set",
    foreignKeys = [
        ForeignKey(
            entity = AccountDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordSetDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_set_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ],
    primaryKeys = ["account_id", "word_set_id"],
    indices = [
        Index("word_set_id")
    ]
)
data class AccountWordSetDbEntity(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "word_set_id") val wordSetId: Long,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean
)
