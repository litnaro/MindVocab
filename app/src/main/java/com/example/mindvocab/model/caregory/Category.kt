package com.example.mindvocab.model.caregory

import com.example.mindvocab.model.word.Word

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val words: List<Word>,
    val wordsKnown: List<Word>,
    val isSelected: Boolean,
)
