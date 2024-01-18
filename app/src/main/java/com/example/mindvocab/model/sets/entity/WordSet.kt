package com.example.mindvocab.model.sets.entity

data class WordSet(
    val id: Long,
    val name: String,
    val photo: String,
    val isSelected: Boolean,
    val wordsCount: Int,
    val accountCompletedWordsCount: Int
)