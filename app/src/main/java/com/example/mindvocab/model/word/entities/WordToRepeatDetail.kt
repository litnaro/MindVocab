package com.example.mindvocab.model.word.entities

data class WordToRepeatDetail(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    val wordSetImage: String,
    val startedAt: String,
    val lastRepeatedAt: String,
    val timesRepeated: Byte
)
