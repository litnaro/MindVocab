package com.example.mindvocab.model.statistic.room.entities

import androidx.room.ColumnInfo

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