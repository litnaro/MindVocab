package com.example.mindvocab.model.statistic.room

import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.account.AccountsRepository
import com.example.mindvocab.model.room.wrapSQLiteException
import com.example.mindvocab.model.statistic.StatisticRepository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.CalendarDayStatistic
import com.example.mindvocab.model.statistic.entities.WordDayStatistic
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

    override suspend fun getStatisticForMonthCalendar(selectedMonth: Int): List<CalendarDayStatistic> = wrapSQLiteException(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val startOfTheMonth = Calendar.getInstance().apply {
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val endOfTheMonth = Calendar.getInstance().apply {
            set(Calendar.MONTH, selectedMonth)
            set(Calendar.DAY_OF_MONTH, this.getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis

        val startedWords = queryStartedWordsInRange(account.id, startOfTheMonth, endOfTheMonth)
        val repeatedWords = queryRepeatedWordsInRange(account.id, startOfTheMonth, endOfTheMonth)

        //Merging two maps
        (startedWords.keys + repeatedWords.keys).associateWith { key ->
            val learningDay = startedWords[key]
            val repeatedDay = repeatedWords[key]
            when {
                learningDay == null -> repeatedDay!!
                repeatedDay == null -> learningDay
                else -> CalendarDayStatistic(
                    day = learningDay.day,
                    isStartedNewWords = true,
                    isRepeatedOldWords = true
                )
            }
        }.values.toList()
    }

    override suspend fun getStartedWordsDetailForDay(date: Long): List<WordDayStatistic> = wrapSQLiteException(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val startOfTheDay = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val endOfTheDay = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis

        statisticDao.getStartedWordsDetailInDateRange(
            account.id,
            startOfTheDay,
            endOfTheDay
        ).map {
            WordDayStatistic(
                id = it.wordDetail.id,
                word = it.wordDetail.word,
                transcription = it.wordDetail.transcription,
                wordSetImage = it.wordDetail.wordSetImage,
                actionDate = it.startedAt
            )
        }
    }

    override suspend fun getRepeatedWordsDetailForDay(date: Long): List<WordDayStatistic> = wrapSQLiteException(ioDispatcher){
        val account = accountsRepository.getAccount().first() ?: throw AuthException()

        val startOfTheDay = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val endOfTheDay = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis

        statisticDao.getRepeatedWordsDetailInDateRange(
            account.id,
            startOfTheDay,
            endOfTheDay
        ).map {
            WordDayStatistic(
                id = it.wordDetail.id,
                word = it.wordDetail.word,
                transcription = it.wordDetail.transcription,
                wordSetImage = it.wordDetail.wordSetImage,
                actionDate = it.repeatedAt
            )
        }
    }

    // TODO remove redundant StatisticDay from query logic
    private suspend fun queryStartedWordsInRange(
        accountId: Long,
        startOfTheMonth: Long,
        endOfTheMonth: Long
    ) : Map<Int, CalendarDayStatistic> {
        val startedWordsForSelectedMonth = statisticDao.getStartedWordsInDateRange(
            accountId = accountId,
            startDate = startOfTheMonth,
            endDate = endOfTheMonth) ?: return emptyMap()

        val startedWordsEachDay = mutableMapOf<Int, CalendarDayStatistic>()

        startedWordsForSelectedMonth.forEach {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = it.startedAt
                //Should be set to 0
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val dayOfTheMonth = calendar.get(Calendar.DAY_OF_MONTH)
            if (!startedWordsEachDay.containsKey(dayOfTheMonth)) {
                startedWordsEachDay[dayOfTheMonth] = CalendarDayStatistic(calendar, isStartedNewWords = true, isRepeatedOldWords = false)
            }
        }

        return startedWordsEachDay
    }

    // TODO remove redundant StatisticDay from query logic
    private suspend fun queryRepeatedWordsInRange(
        accountId: Long,
        startOfTheMonth: Long,
        endOfTheMonth: Long
    ) : Map<Int, CalendarDayStatistic> {
        val repeatedWordsForSelectedMonth = statisticDao.getRepeatedWordsInDateRange(
            accountId = accountId,
            startDate = startOfTheMonth,
            endDate = endOfTheMonth) ?: return emptyMap()

        val repeatedWordsEachDay = mutableMapOf<Int, CalendarDayStatistic>()

        repeatedWordsForSelectedMonth.forEach {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = it.repeatDate
                //Should be set to 0
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val dayOfTheMonth = calendar.get(Calendar.DAY_OF_MONTH)

            if (!repeatedWordsEachDay.containsKey(dayOfTheMonth)) {
                repeatedWordsEachDay[dayOfTheMonth] = CalendarDayStatistic(calendar, isStartedNewWords = false, isRepeatedOldWords = true)
            }
        }

        return repeatedWordsEachDay
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