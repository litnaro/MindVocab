package com.example.mindvocab.model.statistic.entities

data class WordsStatistic (
    val allWordsCount: Int,
    val learnedWords: Int,
    val knownWordsCount: Int,
    val wordsLeftCount: Int
)