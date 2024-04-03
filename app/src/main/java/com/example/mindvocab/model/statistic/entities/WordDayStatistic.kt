package com.example.mindvocab.model.statistic.entities

data class WordDayStatistic(
    val id: Long,
    val word: String,
    val transcription: String,
    val wordSetImage: String,
    val actionDate: Long
)
