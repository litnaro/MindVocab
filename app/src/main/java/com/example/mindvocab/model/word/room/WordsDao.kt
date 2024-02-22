package com.example.mindvocab.model.word.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mindvocab.model.word.room.entities.AccountWordProgressDbEntity
import com.example.mindvocab.model.word.room.entities.WordDbEntity
import com.example.mindvocab.model.word.room.entities.WordForLearningTuple
import com.example.mindvocab.model.word.room.entities.WordWithStatisticTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Query("SELECT * FROM words WHERE word_set_id = :wordSetId")
    fun getWordsByWordSetId(wordSetId: Long): Flow<List<WordDbEntity>>

    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.transcription,\n" +
            "    IFNULL(translations.translation, \"\") AS translation,\n" +
            "    IFNULL(account_word_progress.is_selected, 0) AS is_selected,\n" +
            "    IFNULL(account_word_progress.times_repeated, 0) AS times_repeated,\n" +
            "    IFNULL(account_word_progress.started_at, 0) AS started_at,\n" +
            "    IFNULL(account_word_progress.last_repeated_at, 0) AS last_repeated_at\n" +
            "FROM\n" +
            "    words\n" +
            "LEFT JOIN\n" +
            "    account_word_progress ON words.id = account_word_progress.word_id AND account_word_progress.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    translations ON words.id = translations.word_id AND translations.language_id = 1\n" +
            "WHERE\n" +
            "    words.word_set_id = :wordSetId\n" +
            "GROUP BY\n" +
            "    words.id;\n")
    fun getWordsWithStatisticByWordSetId(wordSetId: Long, accountId: Long): Flow<List<WordWithStatisticTuple>>

    @Transaction
    @Query("SELECT\n" +
            "    words.id,\n" +
            "    words.word,\n" +
            "    words.image,\n" +
            "    words.audio,\n" +
            "    words.transcription,\n" +
            "    words.explanation,\n" +
            "    words.word_set_id\n" +
            "FROM\n" +
            "    words\n" +
            "LEFT JOIN\n" +
            "    word_sets ON words.word_set_id = word_sets.id\n" +
            "LEFT JOIN\n" +
            "    account_word_set ON account_word_set.word_set_id = word_sets.id AND account_word_set.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    account_word_progress ON account_word_progress.word_id = words.id AND account_word_progress.account_id = :accountId\n" +
            "WHERE\n" +
            "    account_word_set.is_selected = 1 AND account_word_progress.started_at = 0\n" +
            "GROUP BY\n" +
            "    words.id\n" +
            "LIMIT 1;")
    suspend fun getWordToLearn(accountId: Long): WordForLearningTuple?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWordToAccountProgress(words: List<AccountWordProgressDbEntity>)

}