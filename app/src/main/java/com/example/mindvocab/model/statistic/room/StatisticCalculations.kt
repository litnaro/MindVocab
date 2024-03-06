package com.example.mindvocab.model.statistic.room

import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import com.example.mindvocab.model.statistic.room.entities.AccountWordsStatisticTuple

object StatisticCalculations {

    //TODO refactor this code
    fun getPercentageByStatistic(statistic: AccountWordsStatisticTuple) : WordsStatisticPercentage {
        val learnedWordsPercentage = (statistic.learnedWordsCount / statistic.allWordsCount.toFloat() * 100).toInt()
        val knownWordsPercentage = (statistic.knownWordsCount / statistic.allWordsCount.toFloat() * 100).toInt()
        val repeatingWordsPercentage = (statistic.repeatingWordsCount / statistic.allWordsCount.toFloat() * 100).toInt()
        val wordsLeft = 100 - learnedWordsPercentage - knownWordsPercentage - repeatingWordsPercentage

        return WordsStatisticPercentage(
            learnedWords = learnedWordsPercentage / 100f,
            knownWords = knownWordsPercentage / 100f,
            repeatingWords = repeatingWordsPercentage / 100f,
            wordsLeft = wordsLeft / 100f,
            //TODO add streak logic
            accountLearningStreak = 0
        )
    }
}