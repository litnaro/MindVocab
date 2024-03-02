package com.example.mindvocab.model.statistic.entities

data class AccountWordsStatisticPercentage(
    val learnedWords: Float,
    val knownWords: Float,
    val repeatingWords: Float,
    val wordsLeft: Float,
    val accountLearningStreak: Int
)