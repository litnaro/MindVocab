package com.example.mindvocab.model.statistic.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class AccountWordsStatisticTuple(
    @ColumnInfo(name = "all_words") val allWordsCount: Int,
    @ColumnInfo(name = "learned_words") val learnedWordsCount: Int,
    @ColumnInfo(name = "known_words") val knownWordsCount: Int,
    @ColumnInfo(name = "repeating_words") val repeatingWordsCount: Int
)

data class AccountCompletedWordSetTuple(
    val id: Long,
    val name: String
)

data class AccountCompletedAchievementsSetTuple(
    @ColumnInfo(name = "all_achievements") val achievementsCount: Int,
    @ColumnInfo(name = "completed_achievements") val achievementsCompleted: Int
)

data class WordWithWordSetImageTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    @ColumnInfo(name = "word_set_image") val wordSetImage: String,
)

data class StartedWordDetailTuple(
    @Embedded val wordDetail: WordWithWordSetImageTuple,
    @ColumnInfo(name = "started_at") val startedAt: Long
)

data class RepeatedWordDetailTuple(
    @Embedded val wordDetail: WordWithWordSetImageTuple,
    @ColumnInfo(name = "repeat_date") val repeatedAt: Long
)