package com.example.mindvocab.model.word.entities

data class WordToRepeat(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    val timesRepeated: Byte,
    val lastRepeatedAt: Long
)
