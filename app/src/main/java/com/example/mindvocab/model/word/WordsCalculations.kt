package com.example.mindvocab.model.word

import com.example.mindvocab.model.word.room.entities.WordWithStatisticTuple
import java.util.Calendar

object WordsCalculations {

    enum class WordStatus {
        NEW,
        IN_PROGRESS,
        KNOWN,
        LEARNED
    }

    private const val TIMES_REPEATED_TO_LEARN = 6

    fun getWordStatusByStatistic(wordWithStatistic: WordWithStatisticTuple) : WordStatus {
        if (wordWithStatistic.startedAt == 0L) {
            return WordStatus.NEW
        }

        // TODO set comment or rewrite it
        if (wordWithStatistic.timesRepeated < TIMES_REPEATED_TO_LEARN
            || wordWithStatistic.timesRepeated != TIMES_REPEATED_TO_LEARN) {
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

    fun getStartOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getWordTimesRepeatedToLearn() = TIMES_REPEATED_TO_LEARN

}