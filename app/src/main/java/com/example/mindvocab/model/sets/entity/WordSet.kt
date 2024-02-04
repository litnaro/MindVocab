package com.example.mindvocab.model.sets.entity

data class WordSet(
    val id: Long,
    val name: String,
    val image: String,
    val isSelected: Boolean,
    val wordsCount: Int,
    val wordsCompleted: Int
)