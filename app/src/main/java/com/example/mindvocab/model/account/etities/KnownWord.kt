package com.example.mindvocab.model.account.etities

import com.example.mindvocab.model.sets.WordSet
import com.example.mindvocab.model.word.Word

data class KnownWord(
    val word: Word,
    val wordSet: WordSet,
    val timeLearned: Int
)
