package com.example.mindvocab.model.repeating.room.entities

import androidx.room.ColumnInfo
import com.example.mindvocab.model.word.entities.WordToRepeat

data class RepeatingWordTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Byte,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long
) {
    fun toRepeatingWord() = WordToRepeat(
        id, word, transcription, explanation, translation, timesRepeated, lastRepeatedAt
    )
}

data class UpdateWordProgressAsRememberedTuple(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Byte,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long
)

data class UpdateWordProgressAsForgottenTuple(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "word_id") val wordId: Long,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long
)

