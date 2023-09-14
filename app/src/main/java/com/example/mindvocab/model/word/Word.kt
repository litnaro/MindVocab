package com.example.mindvocab.model.word

data class Word(
    val id: Int,
    val word: String,
    val transcription: String,
    val photo: String,
    val audio: String,
    val usage: List<String>
)
