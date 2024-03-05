package com.example.mindvocab.model.repeating.room.entities

import androidx.room.ColumnInfo
import com.example.mindvocab.model.word.TimeCalculations
import com.example.mindvocab.model.word.entities.WordToRepeat
import com.example.mindvocab.model.word.entities.WordToRepeatDetail

data class RepeatingWordTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Byte
) {
    fun toWordToRepeat() = WordToRepeat(
        id = id,
        word = word,
        transcription = transcription,
        explanation = explanation,
        translation = translation,
        timesRepeated = timesRepeated
    )
}

data class RepeatingWordDetailTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    @ColumnInfo(name = "word_set_image") val wordSetImage: String,
    @ColumnInfo(name = "started_at") val startedAt: Long,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Byte
) {
    fun toRepeatingWordDetail() = WordToRepeatDetail(
        id = id,
        word = word,
        transcription = transcription,
        explanation = explanation,
        translation = translation,
        wordSetImage = wordSetImage,
        startedAt = TimeCalculations.convertMillisToDateString(startedAt),
        lastRepeatedAt = TimeCalculations.convertMillisToDateString(lastRepeatedAt),
        timesRepeated = timesRepeated,
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

data class StatedWordsTuple(
    @ColumnInfo(name = "started_words_count") val startedWordsCount: Int
)

