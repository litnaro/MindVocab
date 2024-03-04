package com.example.mindvocab.model.word.entities

import com.example.mindvocab.model.word.WordsCalculations

data class WordStatistic(
    val id: Long,
    val word: String,
    val transcription: String,
    val translation: String,
    val learnProgress: Int,
    val wordStatus: WordsCalculations.WordStatus
)
