package com.example.mindvocab.model.statistic.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.AccountWordsStatistic
import com.example.mindvocab.model.statistic.entities.AccountWordsStatisticPercentage
import com.example.mindvocab.model.word.WordsCalculations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomStatisticRepository(
    private val statisticDao: StatisticDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : StatisticRepository {


    override suspend fun getAccountWordsStatistic(): Flow<AccountWordsStatistic> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountWordsStatisticPercentage(): Flow<AccountWordsStatisticPercentage>  = withContext(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        statisticDao.getAccountApplicationStatistic(accountId = account.id, WordsCalculations.getWordTimesRepeatedToLearn())
            .map { StatisticCalculations.getPercentageByStatistic(it) }
    }
}