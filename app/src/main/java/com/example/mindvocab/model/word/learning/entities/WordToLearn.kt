package com.example.mindvocab.model.word.learning.entities

data class WordToLearn(
    val id: Long,

    val word: String,
    val audio: String,
    val image: String,
    val transcription: String,
    val explanation: String,

    val translationList: List<String>,
    val exampleList: List<String>,

)
