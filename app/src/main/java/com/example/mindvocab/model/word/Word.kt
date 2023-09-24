package com.example.mindvocab.model.word

data class Word(
    val id: Int,

    val word: String,
    val audio: String,
    val photo: String,
    val transcription: String,
    val explanation: String,

    val translationList: List<String>,
    val exampleList: List<String>,

    val progress: Byte,
    val lastRepeated: Int,
)
