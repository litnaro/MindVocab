package com.example.mindvocab.model.word.room

import androidx.room.Dao
import androidx.room.Query
import com.example.mindvocab.model.word.room.entities.WordDbEntity
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
            "    IFNULL(accounts_words_progress.started_at, 0) AS started_at,\n" +
            "    IFNULL(accounts_words_progress.last_repeated_at, 0) AS last_repeated_at,\n" +
            "    IFNULL(accounts_words_progress.times_repeated, 0) AS times_repeated\n" +
            "FROM\n" +
            "    words\n" +
            "LEFT JOIN\n" +
            "    accounts_words_progress ON words.id = accounts_words_progress.word_id AND accounts_words_progress.account_id = :accountId\n" +
            "LEFT JOIN\n" +
            "    translations ON words.id = translations.word_id AND translations.language_id = :languageId\n" +
            "WHERE\n" +
            "    words.word_set_id = :wordSetId\n" +
            "GROUP BY\n" +
            "    words.id;\n")
    fun getWordsWithStatisticByWordSetId(accountId: Long, wordSetId: Long, languageId: Int): Flow<List<WordWithStatisticTuple>>

}