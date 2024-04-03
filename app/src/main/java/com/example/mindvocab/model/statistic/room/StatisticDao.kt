package com.example.mindvocab.model.statistic.room

import androidx.room.Dao
import androidx.room.Query
import com.example.mindvocab.model.statistic.room.entities.AccountCompletedAchievementsSetTuple
import com.example.mindvocab.model.statistic.room.entities.AccountCompletedWordSetTuple
import com.example.mindvocab.model.statistic.room.entities.AccountWordsStatisticTuple
import com.example.mindvocab.model.statistic.room.entities.RepeatedWordDetailTuple
import com.example.mindvocab.model.statistic.room.entities.StartedWordDetailTuple
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.room.entities.WordRepeatLogDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {

    @Query("SELECT \n" +
            "    (SELECT COUNT(*) FROM words) AS all_words,\n" +
            "    (SELECT COUNT(*) \n" +
            "     FROM accounts_words_progress\n" +
            "     WHERE account_id = :accountId AND started_at != last_repeated_at AND times_repeated = :timesRepeatedToLearn) AS learned_words,\n" +
            "    (SELECT COUNT(*) \n" +
            "     FROM accounts_words_progress \n" +
            "     WHERE account_id = :accountId AND started_at = last_repeated_at AND times_repeated = :timesRepeatedToLearn) AS known_words,\n" +
            "    (SELECT COUNT(*) \n" +
            "     FROM accounts_words_progress\n" +
            "     WHERE account_id = :accountId AND started_at != 0 AND times_repeated < :timesRepeatedToLearn) AS repeating_words;")
    fun getAccountApplicationStatistic(accountId: Long, timesRepeatedToLearn: Int) : Flow<AccountWordsStatisticTuple>

    @Query("SELECT\n" +
            "    COUNT(*) AS all_achievements,\n" +
            "    (\n" +
            "        SELECT COUNT(*)\n" +
            "        FROM accounts_achievements_progress\n" +
            "        WHERE accounts_achievements_progress.date_achieved != 0\n" +
            "              AND accounts_achievements_progress.account_id = :accountId\n" +
            "    ) AS completed_achievements\n" +
            "FROM\n" +
            "    achievements;")
    fun getAccountAchievementsStatistic(accountId: Long): Flow<AccountCompletedAchievementsSetTuple>

    @Query("SELECT word_sets.id, word_sets.name\n" +
            "FROM word_sets\n" +
            "WHERE NOT EXISTS (\n" +
            "    SELECT words.id\n" +
            "    FROM words\n" +
            "    WHERE words.word_set_id = word_sets.id\n" +
            "      AND NOT EXISTS (\n" +
            "          SELECT accounts_words_progress.word_id\n" +
            "          FROM accounts_words_progress\n" +
            "          WHERE accounts_words_progress.account_id = :accountId\n" +
            "            AND accounts_words_progress.word_id = words.id\n" +
            "            AND accounts_words_progress.times_repeated = :timesRepeatedToLearn\n" +
            "      )\n" +
            ")")
    fun getAccountCompletedWordSets(accountId: Long, timesRepeatedToLearn: Int): Flow<List<AccountCompletedWordSetTuple>>

    @Query("SELECT\n" +
            "   *\n" +
            "FROM\n" +
            "   accounts_words_progress\n" +
            "WHERE\n" +
            "   accounts_words_progress.account_id = :accountId\n" +
            "       AND accounts_words_progress.started_at < :endDate\n" +
            "       AND accounts_words_progress.started_at > :startDate")
    suspend fun getStartedWordsInDateRange(accountId: Long, startDate: Long, endDate: Long) : List<AccountWordProgressDbEntity>?

    @Query("SELECT \n" +
            "   * \n" +
            "FROM \n" +
            "   words_repeat_logs\n" +
            "WHERE\n" +
            "   words_repeat_logs.account_id = :accountId\n" +
            "       AND words_repeat_logs.repeat_date < :endDate\n" +
            "       AND words_repeat_logs.repeat_date > :startDate")
    suspend fun getRepeatedWordsInDateRange(accountId: Long, startDate: Long, endDate: Long) : List<WordRepeatLogDbEntity>?

    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.transcription,\n" +
            "    word_sets.image AS word_set_image,\n" +
            "    accounts_words_progress.started_at\n" +
            "FROM\n" +
            "    accounts_words_progress\n" +
            "JOIN words ON words.id = accounts_words_progress.word_id\n" +
            "JOIN word_sets ON words.word_set_id = word_sets.id\n" +
            "WHERE\n" +
            "    accounts_words_progress.account_id = :accountId\n" +
            "    AND accounts_words_progress.started_at < :endDate\n" +
            "    AND accounts_words_progress.started_at > :startDate")
    suspend fun getStartedWordsDetailInDateRange(accountId: Long, startDate: Long, endDate: Long) : List<StartedWordDetailTuple>

    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.transcription,\n" +
            "    word_sets.image AS word_set_image,\n" +
            "    words_repeat_logs.repeat_date\n" +
            "FROM \n" +
            "    words_repeat_logs\n" +
            "JOIN words ON words.id = words_repeat_logs.word_id\n" +
            "JOIN word_sets ON word_sets.id = words.word_set_id\n" +
            "WHERE \n" +
            "    words_repeat_logs.account_id = :accountId\n" +
            "    AND words_repeat_logs.repeat_date < :endDate\n" +
            "    AND words_repeat_logs.repeat_date > :startDate")
    suspend fun getRepeatedWordsDetailInDateRange(accountId: Long, startDate: Long, endDate: Long) : List<RepeatedWordDetailTuple>

}