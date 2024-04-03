package com.example.mindvocab.model.statistic

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.statistic.entities.CalendarDayStatistic
import com.example.mindvocab.model.statistic.entities.WordDayStatistic

import kotlinx.coroutines.flow.Flow

interface StatisticRepository : Repository {

    /**
     * Get account statistic for account:
     *      - amount of all available words by application;
     *      - amount of words which account learned;
     *      - amount of words which account already know;
     *      - amount of unknown words.
     * @throws AuthException If no user signed in.
     * @throws StorageException If unable to execute SQL query.
     * @return Flow of account statistic.
     */
    suspend fun getWordsStatistic() : Flow<WordsStatistic>

    /**
     * Get everything [getWordsStatistic] does plus how many days account learn words.
     * Convert every parameter of [getWordsStatistic] to float to display as diagram.
     * @throws AuthException If no user signed in.
     * @throws StorageException If unable to execute SQL query.
     * @return Flow of account statistic.
     */
    suspend fun getWordsStatisticPercentage() : Flow<WordsStatisticPercentage>

    /**
     * Get amount of application achievements and amount of achievements which finished by account.
     * @throws AuthException If no user signed in.
     * @throws StorageException If unable to execute SQL query.
     * @return Flow of achievement statistic.
     */
    suspend fun getAchievementStatistic() : Flow<AchievementsStatistic>

    /**
     * Get list of finished word sets names.
     * @throws AuthException If no user signed in.
     * @throws StorageException If unable to execute SQL query.
     * @return Flow of completed word sets names.
     */
    suspend fun getWordSetsStatistic() : Flow<List<String>>

    /**
     * Calculates start and end of the [selectedMonth].
     * Gets all word which were started in that month, and put them to the map,
     * where key - day of month and value - information word started or repeated.
     * Same with repeated words.
     * Then merging 2 maps.
     * Conflict of keys means that in that specific day
     * words were repeated AND started at the same time.
     * Returning all values of merged maps.
     * IMPORTANT: all words time information (hours, minutes, seconds, milliseconds) from database
     * should be set to 0, otherwise it will cause problems in Calendar (problem with first day of the week).
     * If no results return empty list.
     * @param selectedMonth Current month of Calendar on UI side.
     * @throws AuthException If no user signed in.
     * @return List of days for [selectedMonth].
     */
    suspend fun getStatisticForMonthCalendar(selectedMonth: Int) : List<CalendarDayStatistic>

    /**
     * Gets list started words.
     * @param date Information for which day selecting started words.
     * @throws AuthException If no user signed in.
     */
    suspend fun getStartedWordsDetailForDay(date: Long) : List<WordDayStatistic>

    /**
     * Gets list repeated words.
     * @param date Information for which day selecting repeated words.
     * @throws AuthException If no user signed in.
     */
    suspend fun getRepeatedWordsDetailForDay(date: Long) : List<WordDayStatistic>

}