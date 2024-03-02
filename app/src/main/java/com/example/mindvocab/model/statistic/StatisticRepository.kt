package com.example.mindvocab.model.statistic

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.statistic.entities.AccountWordsStatistic
import com.example.mindvocab.model.statistic.entities.AccountWordsStatisticPercentage
import kotlinx.coroutines.flow.Flow

interface StatisticRepository : Repository {

    suspend fun getAccountWordsStatistic() : Flow<AccountWordsStatistic>

    suspend fun getAccountWordsStatisticPercentage() : Flow<AccountWordsStatisticPercentage>

}