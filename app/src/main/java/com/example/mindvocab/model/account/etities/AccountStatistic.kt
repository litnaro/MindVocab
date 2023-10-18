package com.example.mindvocab.model.account.etities

data class AccountStatistic(
    val wordsLearned: Int,
    val wordsKnown: Int,
    val appVisitedInARow: Int,
    val wordsRepeatedInARow: Int,
    val wordsLearnedInARow: Int,
)
