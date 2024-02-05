package com.example.mindvocab.model.word.entities

import com.example.mindvocab.model.word.WordCalculations

data class WordStatistic(
    val id: Long,
    val word: String,
    val transcription: String,
    val translation: String,
    val isSelected: Boolean,
    val learnProgress: Int,
    val wordStatus: WordCalculations.WordStatus
)
