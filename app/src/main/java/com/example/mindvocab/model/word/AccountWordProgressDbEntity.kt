package com.example.mindvocab.model.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.util.TableInfo
import com.example.mindvocab.model.account.room.entities.AccountDbEntity

@Entity(
    tableName = "account_word_progress",
    foreignKeys = [
        ForeignKey(
            entity = AccountDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
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
    primaryKeys = ["account_id", "word_id"],
    indices = [
        Index("word_id")
    ]
)
data class AccountWordProgressDbEntity(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Byte,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long,
    @ColumnInfo(name = "started_at") val startedAt: Long
)
