package com.example.mindvocab.model.word

import com.example.mindvocab.model.word.room.entities.WordWithStatisticTuple

object WordCalculations {

    enum class WordStatus {
        NEW,
        IN_PROGRESS,
        KNOWN,
        LEARNED
    }

    const val TIMES_REPEATED_TO_LEARN = 6

    fun getWordStatusByStatistic(wordWithStatistic: WordWithStatisticTuple) : WordStatus {
        if (wordWithStatistic.startedAt == 0L) {
            return WordStatus.NEW
        }

        if (wordWithStatistic.timesRepeated != 6) {
            return WordStatus.IN_PROGRESS
        }


        if (wordWithStatistic.startedAt == wordWithStatistic.lastRepeatedAt) {
            return WordStatus.KNOWN
        }

        return WordStatus.LEARNED
    }

    fun getProgressOfWord(wordWithStatistic: WordWithStatisticTuple) : Int {
        return ((wordWithStatistic.timesRepeated / TIMES_REPEATED_TO_LEARN.toFloat()) * 100).toInt()
    }

}