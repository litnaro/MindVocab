package com.example.mindvocab.model.word.entities

data class Word(
    val id: Long,
    val word: String,
    val image: String,
    val audio: String,
    val transcription: String,
    val explanation: String,
    val translation: String,
    val exampleList: List<String>
)
