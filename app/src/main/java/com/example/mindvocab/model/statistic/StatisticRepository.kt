package com.example.mindvocab.model.statistic

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import kotlinx.coroutines.flow.Flow

interface StatisticRepository : Repository {

    suspend fun getWordsStatistic() : Flow<WordsStatistic>

    suspend fun getWordsStatisticPercentage() : Flow<WordsStatisticPercentage>

    suspend fun getAchievementStatistic() : Flow<AchievementsStatistic>

    suspend fun getWordSetsStatistic() : Flow<List<String>>

}