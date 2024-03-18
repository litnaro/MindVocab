package com.example.mindvocab.model.word.entities

//TODO too many data classes some of them could be removed
data class WordToRepeat(
    val id: Long,
    val word: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    val timesRepeated: Byte
)
