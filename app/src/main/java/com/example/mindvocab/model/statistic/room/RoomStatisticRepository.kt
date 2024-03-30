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

    override suspend fun getStatisticForMonthCalendar(selectedMonth: Int): List<StatisticDay> {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, selectedMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val dayStartTimes = mutableListOf<Long>()
        val dayEndTimes = mutableListOf<Long>()

        for (i in 0 until maxDayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i + 1)

            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            dayStartTimes.add(calendar.timeInMillis)

            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            dayEndTimes.add(calendar.timeInMillis)
        }

        val listOfStatisticDays = mutableListOf<StatisticDay>()

        for (i in 0 until maxDayOfMonth) {
            val startTime = dayStartTimes[i]
            val endTime = dayEndTimes[i]

            var isWordStarted = false

            val wordsForCurrentDay = statisticDao.getStatisticInDateRange(account.id, startTime, endTime)

            wordsForCurrentDay?.forEach {
                if (it.startedAt in startTime..<endTime) {
                    isWordStarted = true
                    return@forEach
                }
            }

            if (isWordStarted) {
                val day = Calendar.getInstance()
                day.timeInMillis = startTime
                listOfStatisticDays.add(
                    StatisticDay(
                        day = day,
                        isStartedNewWords = isWordStarted,
                        isRepeatedOldWords = false
                    )
                )
            }

        }
        return listOfStatisticDays
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