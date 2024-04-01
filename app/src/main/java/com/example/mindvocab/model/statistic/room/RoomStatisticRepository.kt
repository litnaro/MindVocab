package com.example.mindvocab.model.statistic.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.room.wrapSQLiteException
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.StatisticDay
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import com.example.mindvocab.model.statistic.room.entities.AccountWordsStatisticTuple
import com.example.mindvocab.model.word.WordsCalculations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class RoomStatisticRepository @Inject constructor(
    private val statisticDao: StatisticDao,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : StatisticRepository {

    override suspend fun getWordsStatistic(): Flow<WordsStatistic> = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        statisticDao.getAccountApplicationStatistic(accountId = account.id, WordsCalculations.TIMES_REPEATED_TO_LEARN).map {
            WordsStatistic(
                allWordsCount = it.allWordsCount,
                learnedWords = it.learnedWordsCount,
                knownWordsCount = it.knownWordsCount,
                wordsLeftCount = it.allWordsCount - it.learnedWordsCount - it.knownWordsCount
            )
        }
    }

    override suspend fun getWordsStatisticPercentage(): Flow<WordsStatisticPercentage> = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        statisticDao.getAccountApplicationStatistic(accountId = account.id, WordsCalculations.TIMES_REPEATED_TO_LEARN)
            .map { getPercentageByStatistic(it) }
    }

    override suspend fun getAchievementStatistic(): Flow<AchievementsStatistic> = wrapSQLiteException(ioDispatcher) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        statisticDao.getAccountAchievementsStatistic(account.id).map {
            AchievementsStatistic(
                achievementsCount = it.achievementsCount,
                achievementsCompleted = it.achievementsCompleted
            )
        }
    }

    override suspend fun getWordSetsStatistic(): Flow<List<String>> = wrapSQLiteException(ioDispatcher){
        // TODO maybe also return id to see word set or its statistic
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        statisticDao.getAccountCompletedWordSets(account.id, WordsCalculations.TIMES_REPEATED_TO_LEARN).map { entities ->
            entities.map { it.name }
        }
    }

    override suspend fun getStatisticForMonthCalendar(selectedMonth: Int): List<StatisticDay> = wrapSQLiteException(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val startOfTheMonth = Calendar.getInstance().apply {
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val endOfTheMonth = Calendar.getInstance().apply {
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }

        val wordsForSelectedMonth = statisticDao.getStatisticInDateRange(
            accountId = account.id,
            startDate = startOfTheMonth.timeInMillis,
            endDate = endOfTheMonth.timeInMillis) ?: return@wrapSQLiteException emptyList()

        val daysOfMonthSet = mutableMapOf<Int, StatisticDay>()

        wordsForSelectedMonth.forEach {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = it.startedAt
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val dayOfTheMonth = calendar.get(Calendar.DAY_OF_MONTH)
            if (!daysOfMonthSet.containsKey(dayOfTheMonth)) {
                daysOfMonthSet[dayOfTheMonth] = StatisticDay(calendar, isStartedNewWords = true, isRepeatedOldWords = false)
            }
        }

        daysOfMonthSet.values.toList()
    }

    private fun getPercentageByStatistic(statistic: AccountWordsStatisticTuple) : WordsStatisticPercentage {
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