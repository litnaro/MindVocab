package com.example.mindvocab.model.sets

import com.example.mindvocab.model.word.Word

data class WordSet(
    val id: Int,
    val name: String,
    val photo: String,
    val wordsList: List<Word>
)