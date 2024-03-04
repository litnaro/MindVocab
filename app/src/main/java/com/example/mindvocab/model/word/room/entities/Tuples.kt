package com.example.mindvocab.model.word.room.entities

import androidx.room.ColumnInfo
import com.example.mindvocab.model.word.WordsCalculations
import com.example.mindvocab.model.word.entities.WordStatistic

data class WordWithStatisticTuple(
    val id: Long,
    val word: String,
    val transcription: String,
    val translation: String,
    @ColumnInfo(name = "started_at") val startedAt: Long,
    @ColumnInfo(name = "last_repeated_at") val lastRepeatedAt: Long,
    @ColumnInfo(name = "times_repeated") val timesRepeated: Int
) {
    fun toWordStatistic() = WordStatistic(
        id = id,
        word = word,
        transcription = transcription,
        translation = translation,
        learnProgress = WordsCalculations.getProgressOfWord(this),
        wordStatus = WordsCalculations.getWordStatusByStatistic(this)
    )
}