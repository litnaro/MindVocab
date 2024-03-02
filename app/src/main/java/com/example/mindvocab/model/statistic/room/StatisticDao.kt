package com.example.mindvocab.model.statistic.room

import androidx.room.Dao
import androidx.room.Query
import com.example.mindvocab.model.statistic.room.entities.AccountWordsStatisticTuple
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

}