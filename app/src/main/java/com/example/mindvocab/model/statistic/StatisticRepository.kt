package com.example.mindvocab.model.statistic

import com.example.mindvocab.model.Repository
import com.example.mindvocab.model.statistic.entities.AchievementsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatistic
import com.example.mindvocab.model.statistic.entities.WordsStatisticPercentage
import com.example.mindvocab.model.AuthException
import com.example.mindvocab.model.StorageException
import com.example.mindvocab.model.statistic.entities.StatisticDay

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

    //TODO set comments
    suspend fun getStatisticForMonthCalendar(selectedMonth: Int) : List<StatisticDay>

}