package com.example.mindvocab.model.sets.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.mindvocab.model.sets.entity.WordSet

data class WordSetsWithStatisticTuple(
    @Embedded val wordSet: WordSetDbEntity,
    @ColumnInfo(name = "is_selected") val isSelected: Boolean,
    @ColumnInfo(name = "words_count") val wordsCount: Int,
    @ColumnInfo(name = "words_completed") val wordsCompleted: Int
) {
    fun toWordSet() = WordSet(
        id = wordSet.id,
        name = wordSet.name,
        image = wordSet.image,
        isSelected = isSelected,
        wordsCount = wordsCount,
        wordsCompleted = wordsCompleted
    )
}