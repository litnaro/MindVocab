package com.example.mindvocab.model.sets.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.mindvocab.model.sets.room.entity.AccountWordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetDbEntity
import com.example.mindvocab.model.sets.room.entity.WordSetsWithStatisticTuple
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSetsDao {

    @Query("SELECT\n" +
            "    word_sets.*,\n" +
            "    IFNULL(accounts_word_sets.is_selected, 0) AS is_selected,\n" +
            "    (SELECT COUNT(*) FROM words WHERE words.word_set_id = word_sets.id) AS words_count,\n" +
            "    (SELECT COUNT(*)\n" +
            "        FROM (\n" +
            "            SELECT words.id\n" +
            "            FROM words, accounts_words_progress\n" +
            "            WHERE words.word_set_id = word_sets.id\n" +
            "                AND accounts_words_progress.word_id = words.id\n" +
            "                AND accounts_words_progress.times_repeated = :timesRepeatedToLearn\n" +
            "                AND accounts_words_progress.account_id = :accountId\n" +
            "            GROUP BY words.id)\n" +
            "    ) AS words_completed\n" +
            "FROM\n" +
            "    word_sets\n" +
            "LEFT JOIN\n" +
            "    accounts_word_sets ON accounts_word_sets.account_id = :accountId\n" +
            "                      AND accounts_word_sets.word_set_id = word_sets.id;")
    fun getWordSetWithStatistic(accountId: Long, timesRepeatedToLearn: Int) : Flow<List<WordSetsWithStatisticTuple>>

    @Query("SELECT\n" +
            "    word_sets.*,\n" +
            "    IFNULL(accounts_word_sets.is_selected, 0) AS is_selected,\n" +
            "    (SELECT COUNT(*) FROM words WHERE words.word_set_id = word_sets.id) AS words_count,\n" +
            "    (SELECT COUNT(*)\n" +
            "        FROM (\n" +
            "            SELECT words.id\n" +
            "            FROM words, accounts_words_progress\n" +
            "            WHERE words.word_set_id = word_sets.id\n" +
            "                AND accounts_words_progress.word_id = words.id\n" +
            "                AND accounts_words_progress.times_repeated = :timesRepeatedToLearn\n" +
            "                AND accounts_words_progress.account_id = :accountId\n" +
            "            GROUP BY words.id)\n" +
            "    ) AS words_completed\n" +
            "FROM\n" +
            "    word_sets\n" +
            "LEFT JOIN\n" +
            "    accounts_word_sets ON accounts_word_sets.account_id = :accountId\n" +
            "                      AND accounts_word_sets.word_set_id = word_sets.id\n" +
            "WHERE word_sets.name LIKE :searchQuery;")
    fun getWordSetWithStatistic(accountId: Long, timesRepeatedToLearn: Int, searchQuery: String) : Flow<List<WordSetsWithStatisticTuple>>

    @Upsert
    suspend fun upsertWordSetSelection(accountWordSetSelection: AccountWordSetDbEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordsToAccountProgress(words: List<AccountWordProgressDbEntity>)

    @Transaction
    suspend fun upsertWordSetSelection(accountWordSetSelection: AccountWordSetDbEntity, words: List<AccountWordProgressDbEntity>) {
        upsertWordSetSelection(accountWordSetSelection)
        addWordsToAccountProgress(words)
    }

    @Update
    suspend fun updateWordSet(wordSetDbEntity: WordSetDbEntity)

}